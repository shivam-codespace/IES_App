package com.ies.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailUtils {

    private static final Logger logger = LoggerFactory.getLogger(EmailUtils.class);

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String subject, String body, String to, File file) {
        logger.info(" Sending email started...");
        logger.debug("Subject: {}, To: {}, Attachment: {}", subject, to, 
                     (file != null ? file.getName() : "No Attachment"));

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true = HTML body support

            if (file != null && file.exists()) {
                FileSystemResource resource = new FileSystemResource(file);
                helper.addAttachment(file.getName(), resource);
                logger.info("Attachment added: {}", file.getName());
            }

            mailSender.send(message);
            logger.info("Email sent successfully to {}", to);
            return true;
        } catch (Exception e) {
            logger.error(" Failed to send email to {}. Subject: {}", to, subject, e);
            return false;
        }
    }
}
