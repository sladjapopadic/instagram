package com.itengine.instagram.auth.service;

import com.itengine.instagram.auth.dto.*;
import com.itengine.instagram.auth.enums.ConfirmRegistrationResult;
import com.itengine.instagram.auth.enums.RegistrationResult;
import com.itengine.instagram.email.service.EmailService;
import com.itengine.instagram.email.util.MailValidator;
import com.itengine.instagram.security.jwt.JwtService;
import com.itengine.instagram.user.model.User;
import com.itengine.instagram.user.service.UserService;
import com.itengine.instagram.util.CredentialRegex;
import com.itengine.instagram.util.CredentialValidation;
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

        if (!CredentialValidation.isPatternMatched(CredentialRegex.PASSWORD_REGEX, registrationRequestDto.getPassword())) {
            return new RegistrationResponseDto(RegistrationResult.INVALID_PASSWORD);
        }

        if (!CredentialValidation.isPatternMatched(CredentialRegex.USERNAME_REGEX, registrationRequestDto.getUsername())) {
            return new RegistrationResponseDto(RegistrationResult.INVALID_USERNAME);
        }

        if (!MailValidator.isValid(registrationRequestDto.getEmail())) {
            return new RegistrationResponseDto(RegistrationResult.INVALID_EMAIL);
        }

        if (userService.existsByUsername(registrationRequestDto.getUsername())) {
            return new RegistrationResponseDto(RegistrationResult.UNAVAILABLE_USERNAME);
        }

        if (userService.existsByEmail(registrationRequestDto.getEmail())) {
            return new RegistrationResponseDto(RegistrationResult.UNAVAILABLE_EMAIL);
        }

        String token = jwtService.createToken(registrationRequestDto.getUsername());

        userService.create(registrationRequestDto);
        emailService.sendRegistrationConfirmationMail(registrationRequestDto, token);

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

        User user = userService.activate(confirmationRequestDto.getUsername());
        return new ConfirmationResponseDto(ConfirmRegistrationResult.SUCCESS, jwtService.createToken(confirmationRequestDto.getUsername(), user.getId()));
    }

    public String login(LoginDto loginDto) {
        if (!userService.isActive(loginDto.getUsername())) {
            return null;
        }

        if (!areCredentialsValid(loginDto.getUsername(), loginDto.getPassword())) {
            return null;
        }

        User user = userService.findByUsername(loginDto.getUsername());
        return jwtService.createToken(loginDto.getUsername(), user.getId());
    }

    private boolean areCredentialsValid(String username, String password) {
        UserDetails user = userService.loadUserByUsername(username);

        if (user == null) {
            return false;
        }

        return passwordEncoder.matches(password, user.getPassword());
    }

    public void sendResetPasswordMail(String email) {
        if (!MailValidator.isValid(email)) {
            return;
        }
        String token = jwtService.createToken(email);
        emailService.sendResetPasswordMail(email, token);
    }

    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        if (!jwtService.isValid(resetPasswordDto.getToken())) {
            return;
        }
        String email = jwtService.getTokenSubject(resetPasswordDto.getToken());
        User user = userService.findByEmail(email);

        if(!user.isActive()) {
            return;
        }

        if(!CredentialValidation.isPatternMatched(CredentialRegex.PASSWORD_REGEX, resetPasswordDto.getNewPassword())) {
            return;
        }

        if (passwordEncoder.matches(resetPasswordDto.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
            userService.saveUser(user);
        }
    }
}
