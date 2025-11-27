package com.ies.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ies.binding.*;
import com.ies.entity.EducationEntity;
import com.ies.entity.IncomeEntity;
import com.ies.entity.KidsEntity;
import com.ies.entity.User;
import com.ies.repo.*;
import com.ies.requ.ChildReq;
import com.ies.service.DataCollectionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataCollectionServiceImpl implements DataCollectionService {

    private static final Logger logger = Logger.getLogger(DataCollectionServiceImpl.class.getName());

    @Autowired
    private PlanRepo planRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PlanSelectionRepo seleRepo;

    @Autowired
    private IncomeEntityRepo incomeRepo;

    @Autowired
    private EducationEntityRepo eduRepo;

    @Autowired
    private KidsEntityRepo kidsRepo;

    @Override
    public List<String> getPlansName() {
        logger.info("Fetching all plan names from PlanRepo...");
        try {
            List<String> plans = planRepo.getPlanNames();
            logger.info("Fetched " + plans.size() + " plan names successfully.");
            return plans;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while fetching plan names", e);
            return new ArrayList<>();
        }
    }

    @Override
    public String saveIncomeData(Income income) {
        logger.info("Saving income details for caseNo: " + income.getCaseNo());
        try {
            IncomeEntity incomeEntity = new IncomeEntity();

            Optional<User> findById = userRepo.findById(income.getCaseNo());
            if (findById.isPresent()) {
                User userEntity = findById.get();
                BeanUtils.copyProperties(income, incomeEntity);
                incomeEntity.setCaseNumber(income.getCaseNo());
                incomeEntity.setUser(userEntity);
                incomeRepo.save(incomeEntity);
                logger.info("Income details saved successfully for caseNo: " + income.getCaseNo());
            } else {
                logger.warning("User not found for caseNo: " + income.getCaseNo());
            }
            return "success";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while saving income details", e);
            return "failed";
        }
    }

    @Override
    public String saveEducationData(Education edu) {
        logger.info("Saving education details for caseNo: " + edu.getCaseNo());
        try {
            EducationEntity entity = new EducationEntity();
            Optional<User> findById = userRepo.findById(edu.getCaseNo());
            if (findById.isPresent()) {
                User userEntity = findById.get();
                BeanUtils.copyProperties(edu, entity);
                entity.setCaseNumber(edu.getCaseNo());
                entity.setUser(userEntity);
                eduRepo.save(entity);
                logger.info("Education details saved successfully for caseNo: " + edu.getCaseNo());
            } else {
                logger.warning("User not found for caseNo: " + edu.getCaseNo());
            }
            return "success";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while saving education details", e);
            return "failed";
        }
    }

    @Override
    public String saveKidsData(ChildReq req) {
        logger.info("Saving child details for caseNo: " + req.getCaseNo());
        try {
            List<Child> childs = req.getChild();
            Optional<User> findById = userRepo.findById(req.getCaseNo());

            if (findById.isPresent()) {
                User user = findById.get();
                for (Child k : childs) {
                    KidsEntity entity = new KidsEntity();
                    BeanUtils.copyProperties(k, entity);
                    entity.setCaseNumber(req.getCaseNo());
                    entity.setUser(user);
                    kidsRepo.save(entity);
                }
                logger.info("Saved " + childs.size() + " children for caseNo: " + req.getCaseNo());
            } else {
                logger.warning("User not found for caseNo: " + req.getCaseNo());
            }
            return "success";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while saving child details", e);
            return "failed";
        }
    }

    @Override
    public String saveSelectedPlan(PlanSelection select) {
        logger.info("Saving selected plan for caseNo: " + select.getCaseNo());
        try {
            Optional<User> findById = userRepo.findById(select.getCaseNo());
            if (findById.isPresent()) {
                User userEntity = findById.get();
                BeanUtils.copyProperties(select, userEntity);
                userRepo.save(userEntity);
                logger.info("Selected plan saved successfully for caseNo: " + select.getCaseNo());
            } else {
                logger.warning("User not found for caseNo: " + select.getCaseNo());
            }
            return "success";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while saving selected plan", e);
            return "failed";
        }
    }

    @Override
    public DcSummary getSummary(Integer caseNo) {
        logger.info("Fetching summary for caseNo: " + caseNo);
        DcSummary summary = new DcSummary();
        try {
            IncomeEntity incomeEntity = incomeRepo.findByCaseNumber(caseNo);
            EducationEntity educationEntity = eduRepo.findByCaseNumber(caseNo);
            List<KidsEntity> kidsEntity = kidsRepo.findByCaseNumber(caseNo);

            if (incomeEntity != null) {
                Income income = new Income();
                BeanUtils.copyProperties(incomeEntity, income);
                income.setCaseNo(incomeEntity.getCaseNumber());
                summary.setIncome(income);
            }

            if (educationEntity != null) {
                Education edu = new Education();
                BeanUtils.copyProperties(educationEntity, edu);
                edu.setCaseNo(educationEntity.getCaseNumber());
                summary.setEducation(edu);
            }

            List<Child> childs = new ArrayList<>();
            for (KidsEntity entity : kidsEntity) {
                Child ch = new Child();
                BeanUtils.copyProperties(entity, ch);
                childs.add(ch);
            }
            summary.setChilds(childs);

            logger.info("Summary prepared successfully for caseNo: " + caseNo);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while fetching summary for caseNo=" + caseNo, e);
        }
        return summary;
    }
}
