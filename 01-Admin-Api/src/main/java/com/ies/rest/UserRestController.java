package com.ies.rest;

import com.ies.bindings.DasboardCards;
import com.ies.bindings.LoginForm;
import com.ies.bindings.LoginResponse;
import com.ies.bindings.UnlockAccForm;
import com.ies.bindings.UserAccForm;
import com.ies.entities.UserEntity;
import com.ies.repositories.UserRepository;
import com.ies.service.AccountService;
import com.ies.service.UserService;

 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  // ✅ Import for SLF4J logger
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {

    // ✅ Logger instance using LoggerFactory
    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private AccountService accountService;

    
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm form) {
        logger.info("Received login request for email: " + form.getEmail());

        try {
            String status = userService.login(form);
            logger.info("Login service returned status: " + status);
            
            LoginResponse response = new LoginResponse();
            response.setStatus(status);
            response.setEmail(form.getEmail());

            // ✅ Case 1: Successful login
            if ("Success".equalsIgnoreCase(status)) {
            	UserEntity entity = userRepo.findByEmail(form.getEmail());
                response.setUserId(entity.getUserId());
                return ResponseEntity.ok("Success");
            }

            // ✅ Case 2: Account locked — return 200 OK with message
            if (status.contains("locked") || status.contains("in-active")) {
                return ResponseEntity.ok("Account Locked");
            }

            // ❌ Case 3: Invalid credentials
            if ("Invalid Credentials".equalsIgnoreCase(status)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(status);
            }

            // ✅ Default fallback
            return ResponseEntity.badRequest().body(status);

        } catch (Exception e) {
            logger.error("Error during login for user: " + form.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Login error occurred!");
        }
    }

    
    
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginForm form) {
//
//        logger.info("Received login request for email: " + form.getEmail());
//
//        try {
//            String status = userService.login(form);
//            logger.info("Login service returned status: " + status);
//
//            if ("Success".equals(status)) {
//                logger.info("Login successful for user: " + form.getEmail());
//                return ResponseEntity.ok("Success");
//            } else {
//                logger.warn("Login failed for user: " + form.getEmail() + " | Reason: " + status);
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(status);
//            }
//
//        } catch (Exception e) {
//            logger.error("Error during login for user: " + form.getEmail() + " | Error: " + e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Login error occurred!");
//        }
//    }

    
    // Example: http://localhost:9091/login
//    @GetMapping("/login")
//    public String login(@RequestBody  LoginForm form) {
//    	System.out.println("form"+form);
//        logger.info("Received login request for email: {}", form.getEmail());
//
//        try {
//            String status = userService.login(form);
//            if ("Success".equals(status)) {
//                logger.info("Login successful for user: {}", form.getEmail());
//                return "redirect:/dashboard?email=" + form.getEmail();
//            } else {
//                logger.warn("Login failed for user: {} | Reason: {}", form.getEmail(), status);
//                return status;
//            }
//        } catch (Exception e) {
//            logger.error("Error occurred during login for user {}: {}", form.getEmail(), e.getMessage(), e);
//            return "Login error occurred!";
//        }
//    }

    // added new code.......................................................................................................
    @PutMapping("/unlock")
    public ResponseEntity<String> unlockAccount(@RequestBody UnlockAccForm unlockAccForm) {
        logger.info("Received unlock request for email: {}", unlockAccForm.getEmail());

        // ✅ Input validation
        if (unlockAccForm.getEmail() == null || unlockAccForm.getEmail().trim().isEmpty()) {
            logger.warn("Unlock request failed: Email is missing in the request body");
            return ResponseEntity.badRequest().body("Email is required");
        }

        if (unlockAccForm.getTempPwd() == null || unlockAccForm.getTempPwd().trim().isEmpty()) {
            logger.warn("Unlock request failed: Temporary password is missing for {}", unlockAccForm.getEmail());
            return ResponseEntity.badRequest().body("Temporary password is required");
        }

        if (unlockAccForm.getNewPwd() == null || unlockAccForm.getConfirmPwd() == null ||
            !unlockAccForm.getNewPwd().equals(unlockAccForm.getConfirmPwd())) {
            logger.warn("Unlock request failed for {}: Passwords do not match", unlockAccForm.getEmail());
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        try {
            // ✅ Delegate to service layer
            String result = accountService.unlockUserAccount(unlockAccForm);

            // ✅ Prepare structured responses
            switch (result) {
                case "Account Unlocked":
                    logger.info("Account unlocked successfully for email: {}", unlockAccForm.getEmail());
                    return ResponseEntity.ok(result);

                case "Invalid Temporary Password":
                    logger.warn("Invalid temporary password for email: {}", unlockAccForm.getEmail());
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);

                case "Account Not Found":
                    logger.warn("No user found with email: {}", unlockAccForm.getEmail());
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);

                default:
                    logger.warn("Unlock failed for {}: {}", unlockAccForm.getEmail(), result);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            }

        } catch (Exception e) {
            logger.error("Error unlocking account for {}: {}", unlockAccForm.getEmail(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error unlocking account: " + e.getMessage());
        }
    }

    
    // ended new code............................................................................................................
    
    @GetMapping("/dashboard/{email}")
    public ResponseEntity<DasboardCards> buildDashboard(@PathVariable("email") String email) {
        logger.info("Building dashboard for user: {}", email);

        try {
            UserAccForm user = userService.getUserByEmail(email);
            if (user == null) {
                logger.warn("No user found with email: {}", email);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            DasboardCards dashboardCards = userService.fetchDashboardInfo();
            dashboardCards.setUser(user);

            logger.info("Dashboard built successfully for user: {}", email);
            return new ResponseEntity<>(dashboardCards, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error while building dashboard for user {}: {}", email, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
// // Endpoint to initiate password recovery by email
//    @PostMapping("/recover-password")
//    public ResponseEntity<String> recoverPassword(@RequestBody String email) {
//        boolean isRecovered = userService.recoverPwd(email);
//        if (isRecovered) {
//            return ResponseEntity.ok("OTP sent to email.");
//        } else {
//            return ResponseEntity.status(404).body("Email not found.");
//        }
//    }
    
    @PostMapping("/recover-password")
    public ResponseEntity<String> recoverPassword(@RequestBody String email) {
        boolean otp = userService.recoverPwd(email);

        if (otp) {
            return ResponseEntity.ok("OTP sent to email. Your OTP is: " + otp);
            // ⚠️ Note: Normally, you wouldn't return OTP in response for security.
            // But if you are testing, it's fine temporarily.
        } else {
            return ResponseEntity.status(404).body("Email not found.");
        }
    }
    
//    @PostMapping("/recover-password/{email}")
//    public ResponseEntity<String> recoverPassword(@RequestBody Map<String, String> request) {
//        
//        String email = request.get("email");
//        logger.info("Received password recovery request for: {}", email);
//
//        if (email == null || email.trim().isEmpty()) {
//            logger.warn("Recovery failed: email missing in request body");
//            return ResponseEntity.badRequest().body("Email is required.");
//        }
//
//        boolean sent = userService.recoverPwd(email);
//
//        if (sent) {
//            logger.info("Recovery OTP sent successfully to {}", email);
//            return ResponseEntity.ok("Recovery OTP sent to your registered email.");
//        } else {
//            logger.warn("No account found or email sending failed for {}", email);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found or sending failed.");
//        }
//    }


    
    
    // ✅ LOGOUT ENDPOINT
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        logger.info("Logout request received");

        // If session based:
        //request.getSession().invalidate();

        return ResponseEntity.ok("Logout successful");
    }
    
    
}
