package com.Organizer.organizer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setFrom("organizerweb412@gmail.com");
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);
        logger.info("Email Has Been Sent");
    }

    public void sendEmail(String[] to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setFrom("organizerweb412@gmail.com");
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);
        logger.info("Email Has Been Sent to multiple recipients");
    }
}
