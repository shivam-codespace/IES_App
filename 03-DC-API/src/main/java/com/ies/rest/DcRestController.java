package com.ies.rest;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ies.binding.DcSummary;
import com.ies.binding.Education;
import com.ies.binding.Income;
import com.ies.binding.PlanSelection;
import com.ies.requ.ChildReq;
import com.ies.service.DataCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DcRestController {

    private static final Logger logger = Logger.getLogger(DcRestController.class.getName());

    @Autowired
    private DataCollectionService service;

    @GetMapping("/getPlanName")
    public ResponseEntity<List<String>> getPlanNames() {
        logger.info("Fetching all available plan names...");
        try {
            List<String> plansName = service.getPlansName();
            logger.info("Fetched " + plansName.size() + " plan names.");
            return new ResponseEntity<>(plansName, HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while fetching plan names", e);
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/planInsertion")
    public ResponseEntity<String> planSelection(@RequestBody PlanSelection selectionPlan) {
        logger.info("Received request to save selected plan: " + selectionPlan);
        try {
            String saveSelectedPlan = service.saveSelectedPlan(selectionPlan);
            logger.info("Plan saved successfully: " + saveSelectedPlan);
            return new ResponseEntity<>(saveSelectedPlan, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while saving selected plan: " + selectionPlan, e);
            return new ResponseEntity<>("Failed to save plan", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveIncome")
    public String saveCitizenIncome(@RequestBody Income binding) {
        logger.info("Saving income details: " + binding);
        try {
            String result = service.saveIncomeData(binding);
            logger.info("Income details saved successfully.");
            return result;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while saving income details", e);
            return "Failed to save income data";
        }
    }

    @PostMapping("/saveEdu")
    public String saveEduDetails(@RequestBody Education edu) {
        logger.info("Saving education details: " + edu);
        try {
            String result = service.saveEducationData(edu);
            logger.info("Education details saved successfully.");
            return result;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while saving education details", e);
            return "Failed to save education data";
        }
    }

    @PostMapping("/saveChilds")
    public String saveKids(@RequestBody ChildReq child) {
        logger.info("Saving child details: " + child);
        try {
            String result = service.saveKidsData(child);
            logger.info("Child details saved successfully.");
            return result;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while saving child details", e);
            return "Failed to save child data";
        }
    }

    @GetMapping("/getAllData/{caseNo}")
    public ResponseEntity<DcSummary> getCitizenDetails(@PathVariable Integer caseNo) {
        logger.info("Fetching summary details for caseNo: " + caseNo);
        try {
            DcSummary summary = service.getSummary(caseNo);
            logger.info("Summary fetched successfully for caseNo: " + caseNo);
            return new ResponseEntity<>(summary, HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while fetching summary for caseNo=" + caseNo, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
