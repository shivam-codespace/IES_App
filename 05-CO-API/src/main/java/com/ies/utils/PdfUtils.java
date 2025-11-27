package com.ies.utils;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.ies.entity.EligDetailsEntity;
import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component
public class PdfUtils {

    public void generate(HttpServletResponse response, EligDetailsEntity elig, File f) throws Exception {
        // Step 1: Write PDF to file
        try (FileOutputStream fos = new FileOutputStream(f)) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, fos);
            document.open();

            // Title
            Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20);
            Paragraph paragraph = new Paragraph("Citizen Details", fontTitle);
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph);

            // Table
            PdfPTable table = new PdfPTable(7);
            table.setSpacingBefore(6);

            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(Color.BLUE);

            Font font = FontFactory.getFont(FontFactory.HELVETICA);
            font.setColor(Color.WHITE);

            cell.setPhrase(new Phrase("Elig Id", font)); table.addCell(cell);
            cell.setPhrase(new Phrase("PlanName", font)); table.addCell(cell);
            cell.setPhrase(new Phrase("PlanStatus", font)); table.addCell(cell);
            cell.setPhrase(new Phrase("DenialReason", font)); table.addCell(cell);
            cell.setPhrase(new Phrase("EligStartDate", font)); table.addCell(cell);
            cell.setPhrase(new Phrase("EligEndDate", font)); table.addCell(cell);
            cell.setPhrase(new Phrase("BenefitAmount", font)); table.addCell(cell);

            // Add data
            table.addCell(String.valueOf(elig.getEdId()));
            table.addCell(elig.getPlanName());
            table.addCell(elig.getPlanStatus());
            table.addCell(elig.getDenialReason() != null ? elig.getDenialReason() : "N/A");
            table.addCell(elig.getEligStartDate() != null ? elig.getEligStartDate().toString() : "N/A");
            table.addCell(elig.getEligEndDate() != null ? elig.getEligEndDate().toString() : "N/A");
            table.addCell(elig.getBenefitAmt() != 0 ? String.valueOf(elig.getBenefitAmt()) : "N/A");

            document.add(table);
            document.close();
        }

        // Step 2: Copy file into HTTP response (if response is provided)
        if (response != null) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());

            try (FileInputStream fis = new FileInputStream(f);
                 OutputStream os = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.flush();
            }
        }
    }

    // Add methods for Approved/Denied notices
    public void generateApprovedNotice(EligDetailsEntity elig, File file) throws Exception {
        generate(null, elig, file);
    }

    public void generateDeniedNotice(EligDetailsEntity elig, File file) throws Exception {
        generate(null, elig, file);
    }
}
