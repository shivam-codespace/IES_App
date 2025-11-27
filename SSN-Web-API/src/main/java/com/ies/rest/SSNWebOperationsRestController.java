package com.ies.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/ssn-web-api")
public class SSNWebOperationsRestController {

    // Create Logger instance
    private static final Logger logger = Logger.getLogger(SSNWebOperationsRestController.class.getName());

    @GetMapping("/find/{ssn}")
    public ResponseEntity<String> getStateBySSN(@PathVariable Long ssn) {

        logger.info("Received request to find state for SSN: " + ssn);

        // Validate SSN length
        if (String.valueOf(ssn).length() != 9) {
            logger.warning("Invalid SSN length: " + ssn);
            return new ResponseEntity<>("invalid ssn", HttpStatus.BAD_REQUEST);
        }

        // Extract first 2 digits
        int ssnCode = Integer.parseInt(String.valueOf(ssn).substring(0, 2));
        logger.fine("SSN prefix code extracted: " + ssnCode);

        String stateName;

        switch (ssnCode) {
            case 10: stateName = "RI"; break;
            case 20: stateName = "Ohio"; break;
            case 30: stateName = "Texas"; break;
            case 40: stateName = "California"; break;
            case 50: stateName = "Florida"; break;
            default: 
                stateName = "invalid SSN"; 
                logger.warning("SSN code not recognized: " + ssnCode);
                break;
        }

        logger.info("Returning state name: " + stateName + " for SSN: " + ssn);
        return new ResponseEntity<>(stateName, HttpStatus.OK);
    }
}
