package com.itengine.instagram.email.service;

import com.itengine.instagram.auth.dto.RegistrationRequestDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendRegistrationConfirmationMail(RegistrationRequestDto registrationRequestDto, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@fakeinstagram.com");
        message.setTo(registrationRequestDto.getEmail());
        message.setSubject("Confirm registration");
        message.setText("To confirm the registration, follow the link:\n" + token);
        javaMailSender.send(message);
    }

    @Async
    public void sendResetPasswordMail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@fakeinstagram.com");
        message.setTo(email);
        message.setSubject("Reset password");
        message.setText("A request has been received to change the password for your fake instagram account.\n" + token);
        javaMailSender.send(message);
    }

}
