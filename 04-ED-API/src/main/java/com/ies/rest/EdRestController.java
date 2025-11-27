package com.ies.rest;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ies.binding.Entries;
import com.ies.service.EdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EdRestController {

    private static final Logger logger = Logger.getLogger(EdRestController.class.getName());

    @Autowired
    private EdService service;

    @GetMapping("/checkEligibility/{caseNo}")
    public ResponseEntity<String> eligibilityCheck(@PathVariable Integer caseNo) {
        logger.info("Received request to check eligibility for caseNo: " + caseNo);
        try {
            String elig = service.eligibilityCheck(caseNo);
            logger.info("Eligibility check completed for caseNo: " + caseNo + ", Result: " + elig);
            return new ResponseEntity<>(elig, HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while checking eligibility for caseNo: " + caseNo, e);
            return new ResponseEntity<>("Error while checking eligibility", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCitizensData")
    public ResponseEntity<List<Entries>> loadData() {
        logger.info("Received request to fetch all citizen data...");
        try {
            List<Entries> allData = service.getAllData();
            logger.info("Fetched " + (allData != null ? allData.size() : 0) + " records successfully.");
            return new ResponseEntity<>(allData, HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while fetching citizens data", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
