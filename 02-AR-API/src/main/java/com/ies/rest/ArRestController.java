package com.ies.rest;

import com.ies.bindings.App;
import com.ies.service.ArService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/apps") // ✅ Common base path
public class ArRestController {

    private static final Logger logger = Logger.getLogger(ArRestController.class.getName());

    @Autowired
    private ArService arService;

    // ✅ 1. Create a new application
    @PostMapping("/")
    public ResponseEntity<String> createApp(@RequestBody App app) {
        logger.info("Received request to create application: " + app);
        try {
            String status = arService.createApplication(app);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while creating application", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create application");
        }
    }

    // ✅ 2. Get all applications by userId (admin or caseworker)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<App>> getApps(@PathVariable Integer userId) {
        try {
            List<App> apps = arService.fetchApps(userId);
            if (apps.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(apps);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while fetching applications", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✅ 3. Get application by case number
    @GetMapping("/{caseNum}")
    public ResponseEntity<App> getAppByCaseNum(@PathVariable Long caseNum) {
        try {
            App app = arService.getAppByCaseNum(caseNum);
            return app != null ? ResponseEntity.ok(app) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching application with caseNum: " + caseNum, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✅ 4. Update application status (Admin/Caseworker)
    @PutMapping("/{caseNum}/status/{status}")
    public ResponseEntity<String> updateAppStatus(
            @PathVariable Long caseNum,
            @PathVariable String status) {
        try {
            String msg = arService.updateAppStatus(caseNum, status);
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating status for caseNum: " + caseNum, e);
            return ResponseEntity.internalServerError().body("Failed to update status");
        }
    }

    // ✅ 5. Search/filter applications (Admin dashboard use)
    @GetMapping("/filter")
    public ResponseEntity<List<App>> filterApps(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String planName) {
        try {
            List<App> apps = arService.filterApps(status, planName);
            return apps.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(apps);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while filtering applications", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
