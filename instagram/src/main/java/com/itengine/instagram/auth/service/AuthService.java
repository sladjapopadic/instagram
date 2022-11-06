package com.itengine.instagram.auth.service;

import com.itengine.instagram.auth.dto.*;
import com.itengine.instagram.auth.enums.ConfirmRegistrationResult;
import com.itengine.instagram.auth.enums.RegistrationResult;
import com.itengine.instagram.auth.enums.ResetPasswordResult;
import com.itengine.instagram.email.service.EmailService;
import com.itengine.instagram.email.util.MailValidator;
import com.itengine.instagram.security.jwt.JwtService;
import com.itengine.instagram.user.model.User;
import com.itengine.instagram.user.service.UserService;
import com.itengine.instagram.util.CredentialValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public RegistrationResponseDto register(RegistrationRequestDto registrationRequestDto) {

        CredentialValidation.validatePasswordFormat(registrationRequestDto.getPassword());

        CredentialValidation.validateUsernameFormat(registrationRequestDto.getUsername());

        MailValidator.validateEmail(registrationRequestDto.getEmail());

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

        String username = loginDto.getUsername();

        if (!userService.isActive(username)) {
            LOGGER.warn("User " + username + " login failed, inactive user");
            return null;
        }

        if (!areCredentialsValid(username, loginDto.getPassword())) {
            return null;
        }

        User user = userService.findByUsername(username);
        return jwtService.createToken(username, user.getId());
    }

    private boolean areCredentialsValid(String username, String password) {
        UserDetails user = userService.loadUserByUsername(username);

        if (user == null) {
            return false;
        }

        return passwordEncoder.matches(password, user.getPassword());
    }

    public void sendResetPasswordMail(String email) {
        MailValidator.validateEmail(email);

        User user = userService.findByEmail(email);

        if (user == null || !user.isActive()) {
            LOGGER.warn("User is not active or doesn't exits by given email");
            return;
        }

        String token = jwtService.createToken(email);
        emailService.sendResetPasswordMail(email, token);
    }

    public ResetPasswordResponseDto resetPassword(ResetPasswordDto resetPasswordDto) {
        if (!jwtService.isValid(resetPasswordDto.getToken())) {
            return new ResetPasswordResponseDto(ResetPasswordResult.INVALID_TOKEN);
        }
        String email = jwtService.getTokenSubject(resetPasswordDto.getToken());
        User user = userService.findByEmail(email);

        CredentialValidation.validatePasswordFormat(resetPasswordDto.getNewPassword());

        if (!passwordEncoder.matches(resetPasswordDto.getOldPassword(), user.getPassword())) {
            return new ResetPasswordResponseDto(ResetPasswordResult.OLD_PASSWORD_NOT_MATCHED);
        }

        user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
        userService.saveUser(user);

        return new ResetPasswordResponseDto(ResetPasswordResult.SUCCESS);
    }
}
