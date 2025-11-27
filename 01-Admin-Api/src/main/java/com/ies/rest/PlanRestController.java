package com.ies.rest;

import java.util.List;

import com.ies.bindings.PlanForm;
import com.ies.bindings.UserAccForm;
import com.ies.constants.AppConstants;
import com.ies.service.PlanService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  // ✅ import for LoggerFactory
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlanRestController {

    private static final Logger logger = LoggerFactory.getLogger(PlanRestController.class);  // ✅ Logger instance

    @Autowired
    private PlanService planService;

    @PostMapping("/savePlan")
    public ResponseEntity<String> savePlan(@RequestBody PlanForm form) {
        logger.info("Received request to save plan: {}", form);

        boolean status = planService.createPlan(form);
        if (status) {
            logger.info("Plan created successfully: {}", form.getPlanName());
            return new ResponseEntity<>(AppConstants.PLAN_CREATED, HttpStatus.CREATED);
        } else {
            logger.error("Plan creation failed for plan: {}", form.getPlanName());
            return new ResponseEntity<>(AppConstants.PLAN_CREATION_FAILD, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/plans")
    public ResponseEntity<List<PlanForm>> fetchAllPlan() {
        logger.info("Fetching all plans");

        List<PlanForm> fetchPlans = planService.fetchPlans();
        logger.debug("Fetched {} plans", fetchPlans.size());

        return new ResponseEntity<>(fetchPlans, HttpStatus.OK);
    }

    @PutMapping("/plan/{planId}/{status}")
    public ResponseEntity<String> changePlanStatus(
            @PathVariable("planId") Integer planId,
            @PathVariable("status") String status) {

        logger.info("Received request to change plan status. planId={}, newStatus={}", planId, status);

        String result = planService.changePlanStatus(planId, status);

        if (AppConstants.PLAN_STATUS_SUCCESS.equals(result)) {
            logger.info("Plan status updated successfully for planId {}", planId);
            return ResponseEntity.ok(result);
        } else {
            logger.warn("Failed to update plan status for planId {}", planId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }


	@GetMapping("/plan/{planId}")
	public ResponseEntity<PlanForm> getUser(@PathVariable("planId") Integer planId) {
		
		PlanForm planById = planService.getPlanById(planId);

		logger.debug(AppConstants.PLAN_FETCHING_SUCCESS);
		return new ResponseEntity<>(planById, HttpStatus.OK);
	}
}
