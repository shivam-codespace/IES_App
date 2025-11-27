package com.ies.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ies.binding.Entries;
import com.ies.entity.*;
import com.ies.repo.*;
import com.ies.service.EdService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EdServiceImpl implements EdService {

    private static final Logger logger = Logger.getLogger(EdServiceImpl.class.getName());

    @Autowired
    private EligDetailsEntityRepo eligRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private IncomeEntityRepo incomeRepo;

    @Autowired
    private EducationEntityRepo eduRepo;

    @Autowired
    private KidsEntityRepo kidsRepo;

    @Override
    public String eligibilityCheck(Integer caseNo) {
        logger.info("Eligibility check started for caseNo: " + caseNo);
        try {
            User user = userRepo.findById(caseNo).orElse(null);
            if (user == null) {
                logger.warning("No user found for caseNo: " + caseNo);
                return "User Not Found";
            }

            String planName = user.getPlanName();
            logger.info("User plan: " + planName);

            Double totalIncome = 0.0;

            // SNAP
            if ("SNAP".equals(planName)) {
                IncomeEntity income = incomeRepo.findByCaseNumber(caseNo);
                totalIncome = income.getMonthlySalaryIncome() + income.getPropertyIncome() + income.getRentIncome();
                logger.info("Total Income for SNAP: " + totalIncome);

                if (totalIncome < 300) {
                    saveApproved(user, planName, 250);
                    return "Approved";
                } else {
                    saveDenied(user, planName, "Income Condition Failed");
                    return "Denied";
                }
            }

            // CCAP
            if ("CCAP".equals(planName)) {
                IncomeEntity income = incomeRepo.findByCaseNumber(caseNo);
                totalIncome = income.getMonthlySalaryIncome() + income.getPropertyIncome() + income.getRentIncome();
                logger.info("Total Income for CCAP: " + totalIncome);

                List<KidsEntity> kids = kidsRepo.findByCaseNumber(caseNo);
                long invalidKids = kids.stream().filter(k -> k.getKidAge() > 16).count();
                logger.info("Kids over 16: " + invalidKids);

                if (totalIncome < 500 && invalidKids == 0) {
                    saveApproved(user, planName, 250);
                    return "Approved";
                } else if (invalidKids > 0) {
                    saveDenied(user, planName, "Kids Age Above 16");
                    return "Denied";
                } else {
                    saveDenied(user, planName, "Income Condition Failed");
                    return "Denied";
                }
            }

            // Medicaid
            if ("Medicaid".equals(planName)) {
                IncomeEntity income = incomeRepo.findByCaseNumber(caseNo);
                totalIncome = income.getMonthlySalaryIncome() + income.getPropertyIncome() + income.getRentIncome();
                logger.info("Total Income for Medicaid: " + totalIncome);

                if (totalIncome < 320) {
                    saveApproved(user, planName, 150);
                    return "Approved";
                } else {
                    saveDenied(user, planName, "Income Condition Failed");
                    return "Denied";
                }
            }

            // RIW
            if ("RIW".equals(planName)) {
                IncomeEntity income = incomeRepo.findByCaseNumber(caseNo);
                EducationEntity education = eduRepo.findByCaseNumber(caseNo);
                logger.info("Salary: " + income.getMonthlySalaryIncome() + ", Degree: " + education.getHighestDegree());

                if (income.getMonthlySalaryIncome() == 0 && "Bachelor".equals(education.getHighestDegree())) {
                    saveApproved(user, planName, 250);
                    return "Approved";
                } else if (income.getMonthlySalaryIncome() != 0) {
                    saveDenied(user, planName, "Salary Condition Failed");
                    return "Denied";
                } else {
                    saveDenied(user, planName, "Education Condition Failed");
                    return "Denied";
                }
            }

            // Medicare
            if ("Medicare".equals(planName)) {
                LocalDate dob = LocalDate.parse(user.getDob());
                int age = Period.between(dob, LocalDate.now()).getYears();
                logger.info("User age: " + age);

                if (age < 65) {
                    saveApproved(user, planName, 250);
                    return "Approved";
                } else {
                    saveDenied(user, planName, "Age Condition Failed");
                    return "Denied";
                }
            }

            logger.warning("Unknown plan name: " + planName);
            return "Unknown Plan";

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while checking eligibility for caseNo: " + caseNo, e);
            return "Error Processing";
        }
    }

    @Override
    public List<Entries> getAllData() {
        logger.info("Fetching all eligibility records...");
        List<EligDetailsEntity> entities = eligRepo.findAll();
        List<Entries> list = new ArrayList<>();
        for (EligDetailsEntity ent : entities) {
            Entries entry = new Entries();
            BeanUtils.copyProperties(ent, entry);
            list.add(entry);
        }
        logger.info("Total records fetched: " + list.size());
        return list;
    }

    //  Helper Methods
    private void saveApproved(User user, String planName, double benefitAmt) {
        EligDetailsEntity entity = new EligDetailsEntity();
        entity.setPlanName(planName);
        entity.setPlanStatus("Approved");
        entity.setEligStartDate(LocalDate.now());
        entity.setEligEndDate(LocalDate.now().plusYears(2));
        entity.setBenefitAmt(benefitAmt);
        entity.setDenialReason("NA");
        entity.setUser(user);
        eligRepo.save(entity);

        user.setElig(entity);
        userRepo.save(user);
        logger.info("Eligibility Approved for user: " + user.getCaseNo() + ", Plan: " + planName);
    }

    private void saveDenied(User user, String planName, String reason) {
        EligDetailsEntity entity = new EligDetailsEntity();
        entity.setPlanName(planName);
        entity.setPlanStatus("Denied");
        entity.setDenialReason(reason);
        entity.setUser(user);
        eligRepo.save(entity);

        user.setElig(entity);
        userRepo.save(user);
        logger.info("Eligibility Denied for user: " + user.getCaseNo() + ", Plan: " + planName + ", Reason: " + reason);
    }
}
