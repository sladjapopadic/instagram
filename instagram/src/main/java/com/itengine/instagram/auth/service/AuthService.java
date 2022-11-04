package com.itengine.instagram.auth.service;

import com.itengine.instagram.auth.dto.*;
import com.itengine.instagram.auth.enums.ConfirmRegistrationResult;
import com.itengine.instagram.auth.enums.RegistrationResult;
import com.itengine.instagram.email.service.EmailService;
import com.itengine.instagram.email.util.MailValidator;
import com.itengine.instagram.security.jwt.JwtService;
import com.itengine.instagram.user.model.User;
import com.itengine.instagram.user.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private static final String USERNAME_REGEX = "^[a-z\\d_.-]+$";
    private static final String PASSWORD_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?\\d)(?=.*?[#?!@$%^&*-.,]).{8,}$";

    public AuthService(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public RegistrationResponseDto register(RegistrationRequestDto registrationRequestDto) {

        if (!isPatternMatched(PASSWORD_REGEX, registrationRequestDto.getPassword())) {
            return new RegistrationResponseDto(RegistrationResult.INVALID_PASSWORD);
        }

        if (!isPatternMatched(USERNAME_REGEX, registrationRequestDto.getUsername())) {
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

    private boolean isPatternMatched(String regex, String valueToMatch) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(valueToMatch);
        return matcher.matches();
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

        if(!isPatternMatched(PASSWORD_REGEX, resetPasswordDto.getNewPassword())) {
            return;
        }

        if (passwordEncoder.matches(resetPasswordDto.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
            userService.saveUser(user);
        }
    }
}
