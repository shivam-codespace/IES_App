package com.ies.service.impl;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ies.entity.CaseWorker;
import com.ies.entity.CorrespondenceEntity;
import com.ies.entity.EligDetailsEntity;
import com.ies.entity.User;
import com.ies.repo.CorrespondenceEntityRepo;
import com.ies.repo.EligDetailsEntityRepo;
import com.ies.repo.UserRepo;
import com.ies.service.CorrespondenceService;
import com.ies.utils.EmailUtils;
import com.ies.utils.PdfUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorrespondenceServiceImpl implements CorrespondenceService {

    private static final Logger logger = LoggerFactory.getLogger(CorrespondenceServiceImpl.class);

    @Autowired
    private CorrespondenceEntityRepo corrRepo;

    @Autowired
    private EligDetailsEntityRepo eligDetailsEntityRepo;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private PdfUtils pdfUtils;

    @Override
    public String generateNotice(Integer caseNo) {
        logger.info("generateNotice() started for caseNo: {}", caseNo);

        User user = userRepo.findById(caseNo).orElse(null);
        if (user == null) {
            logger.error("User not found for caseNo: {}", caseNo);
            return "User not found";
        }
        logger.info("User found: {}", user.getFullname());

        EligDetailsEntity elig = user.getElig();
        logger.info("Eligibility details retrieved for plan: {}", elig.getPlanName());

        CorrespondenceEntity entity = new CorrespondenceEntity();
        entity.setCaseNumber(caseNo);
        entity.setNoticeStatus("P");  
        entity.setCreatedDate(LocalDate.now());
        entity.setCaseNo(user);
        entity.setEdId(elig);

        corrRepo.save(entity);
        logger.info("Notice created and saved with status Pending for caseNo: {}", caseNo);

        return "success";
    }

    @Override
    public boolean exportPdf(HttpServletResponse response, Integer caseNo) throws Exception {
        logger.info("exportPdf() started for caseNo: {}", caseNo);

        User user = userRepo.findById(caseNo).orElse(null);
        if (user == null) {
            logger.error("User not found for caseNo: {}", caseNo);
            return false;
        }

        EligDetailsEntity elig = user.getElig();
        logger.info("Eligibility found for plan: {}", elig.getPlanName());

        File f = new File("Citizen.pdf");
        pdfUtils.generate(response, elig, f);
        logger.info("PDF generated: {}", f.getAbsolutePath());

        CorrespondenceEntity entity = new CorrespondenceEntity();
        entity.setNoticeUrl("Citizen.pdf");
        entity.setNoticeStatus("Pending");
        entity.setEdId(elig);
        entity.setCreatedDate(user.getCreateDate());
        entity.setPrintDate(LocalDate.now());
        entity.setCaseNo(user);
        corrRepo.save(entity);
        
        logger.info("Correspondence entity saved with status Pending");

        if (f.delete()) {
            logger.info("Temp PDF deleted");
        } else {
            logger.warn("Failed to delete temp PDF: {}", f.getName());
        }

        return true;
    }

    @Override
    public void processPendingTriggers() {
        logger.info("processPendingTriggers() started...");

        List<CorrespondenceEntity> records = corrRepo.findByNoticeStatus("P");
        logger.info("Found {} pending records", records.size());

        for (CorrespondenceEntity entity : records) {
            processEachRecord(entity);
        }
        logger.info("All pending triggers processed");
    }

    private void processEachRecord(CorrespondenceEntity correspondence) {
        logger.info("Processing record for caseNo: {}", correspondence.getCaseNumber());

        EligDetailsEntity eligDetails =
                eligDetailsEntityRepo.findByUserCaseNo(correspondence.getCaseNumber());
        if (eligDetails == null) {
            logger.error("No eligibility details found for caseNo: {}", correspondence.getCaseNumber());
            return;
        }

        String planStatus = eligDetails.getPlanStatus();
        logger.info("Plan status: {}", planStatus);

        File pdfFile = null;

        if ("Approved".equalsIgnoreCase(planStatus)) {
            pdfFile = generateApprovedNotice(eligDetails);
        } else if ("Denied".equalsIgnoreCase(planStatus)) {
            pdfFile = generateDeniedNotice(eligDetails);
        }

        if (pdfFile != null) {
            logger.info("PDF generated: {}", pdfFile.getName());
            String fileUrl = storePdfInS3(pdfFile);
            logger.info("PDF stored at URL: {}", fileUrl);

            boolean isUpdated = updateProcessedRecord(correspondence, fileUrl);

            if (isUpdated) {
                logger.info("Record updated, sending email...");
                try {
                    emailUtils.sendEmail(
                            "Eligibility Notice",
                            "Please find your notice attached",
                            correspondence.getCaseNo().getEmail(),
                            pdfFile
                    );
                    logger.info("Email sent successfully");
                } catch (Exception e) {
                    logger.error("Failed to send email for caseNo: {}", correspondence.getCaseNumber(), e);
                }
            }
        }
    }

    private boolean updateProcessedRecord(CorrespondenceEntity correspondence, String fileUrl) {
        logger.info("Updating record for caseNo: {}", correspondence.getCaseNumber());

        correspondence.setNoticeStatus("C");
        correspondence.setNoticeUrl(fileUrl);
        corrRepo.save(correspondence);

        logger.info("Record updated with Completed status and fileUrl: {}", fileUrl);
        return true;
    }

    private String storePdfInS3(File pdfFile) {
        logger.debug("Storing PDF in S3 (dummy implementation)...");
        return "https://s3.amazonaws.com/bucket/" + pdfFile.getName();
    }

    private File generateDeniedNotice(EligDetailsEntity eligDetails) {
        logger.info("Generating Denied Notice for caseNo: {}", eligDetails.getCaseNo());
        File deniedPdf = new File("DeniedNotice_" + eligDetails.getCaseNo() + ".pdf");
        try {
			pdfUtils.generateDeniedNotice(eligDetails, deniedPdf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return deniedPdf;
    }

    private File generateApprovedNotice(EligDetailsEntity eligDetails) {
        logger.info("Generating Approved Notice for caseNo: {}", eligDetails.getCaseNo());
        File approvedPdf = new File("ApprovedNotice_" + eligDetails.getCaseNo() + ".pdf");
        try {
			pdfUtils.generateApprovedNotice(eligDetails, approvedPdf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return approvedPdf;
    }
}
