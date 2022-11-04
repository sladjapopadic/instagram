package com.itengine.instagram.auth.service;

import com.itengine.instagram.auth.enums.ConfirmRegistrationResult;
import com.itengine.instagram.auth.enums.RegistrationResult;
import com.itengine.instagram.auth.model.*;
import com.itengine.instagram.email.service.EmailService;
import com.itengine.instagram.security.jwt.JwtService;
import com.itengine.instagram.user.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthService(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public RegistrationResponseDto register(RegistrationRequestDto registrationRequestDto) {

        if (!userService.exists(registrationRequestDto.getUsername())) {
            return new RegistrationResponseDto(RegistrationResult.UNAVAILABLE_USERNAME);
        }

        String token = jwtService.createToken(registrationRequestDto.getUsername());

        userService.create(registrationRequestDto);
        emailService.sendConfirmationMail(registrationRequestDto, token);

        return new RegistrationResponseDto(RegistrationResult.SUCCESS);
    }

    public ConfirmationResponseDto confirmRegistration(ConfirmationRequestDto confirmationRequestDto) {

        if (!jwtService.isValid(confirmationRequestDto.getToken())) {
            return new ConfirmationResponseDto(ConfirmRegistrationResult.INVALID_TOKEN);
        }

        if (!areCredentialsValid(confirmationRequestDto.getUsername(), confirmationRequestDto.getPassword())) {
            return new ConfirmationResponseDto(ConfirmRegistrationResult.INVALID_CREDENTIALS);
        }

        if (userService.isActive(confirmationRequestDto.getUsername())) {
            return new ConfirmationResponseDto(ConfirmRegistrationResult.ALREADY_CONFIRMED);
        }

        userService.activate(confirmationRequestDto.getUsername());
        return new ConfirmationResponseDto(ConfirmRegistrationResult.SUCCESS, jwtService.createToken(confirmationRequestDto.getUsername()));
    }

    public String login(LoginDto loginDto) {
        if (!userService.isActive(loginDto.getUsername())) {
            return null;
        }

        if (!areCredentialsValid(loginDto.getUsername(), loginDto.getPassword())) {
            return null;
        }

        return jwtService.createToken(loginDto.getUsername());
    }

    private boolean areCredentialsValid(String username, String password) {
        UserDetails user = userService.loadUserByUsername(username);

        if (user == null) {
            return false;
        }

        return passwordEncoder.matches(password, user.getPassword());
    }
}
