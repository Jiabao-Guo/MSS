package com.jasper.munselfservice.v1.service;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;

@Service
public class MailerService {
    @Resource
    JavaMailSender mailSender;

    @Value("${spring.mail.username}") private String sender;
    public void sendPasswordEmail(String email, String password) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(sender);
        mail.setTo(email);
        mail.setSubject("Your MSS Account Password");
        mail.setText(
            "Welcome to MSS! Your initial password is: " + password + "\n\n"
            + "Please change your password & delete this mail after you login.\n\n"
            + "Regards,\n"
            + "Jasper"
        );
        Executors.newFixedThreadPool(1).submit(() -> mailSender.send(mail));
    }
}
