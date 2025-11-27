package com.ies.email;


public interface EmailService {
    boolean sendEmail(String to, String subject, String body);
}

