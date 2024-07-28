package com.Organizer.organizer;

import org.hibernate.annotations.TimeZoneStorage;
import org.springframework.beans.factory.annotation.Autowired;

import com.Organizer.organizer.services.EmailService;

import jakarta.validation.constraints.Email;

@SpringBootTest
public class EmailSenderTest {
    @Autowired
    private EmailService emailService;
    @Test
    void sendEmailTest() {
        emailService.sendEmail("nameetsk1@gmail.com", "Email from Organizer", "Hey");
    }
}