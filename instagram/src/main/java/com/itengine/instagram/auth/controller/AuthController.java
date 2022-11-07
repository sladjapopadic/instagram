package com.itengine.instagram.auth.controller;

import com.itengine.instagram.auth.dto.*;
import com.itengine.instagram.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDto> register(@RequestBody RegistrationRequestDto registrationRequestDto) throws IOException {
        return new ResponseEntity<>(authService.register(registrationRequestDto), HttpStatus.OK);
    }

    @PatchMapping("/confirm")
    public ResponseEntity<ConfirmationResponseDto> confirmRegistration(@RequestBody ConfirmationRequestDto confirmationRequestDto) {
        return new ResponseEntity<>(authService.confirmRegistration(confirmationRequestDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return new ResponseEntity<>(authService.login(loginRequestDto), HttpStatus.OK);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<Void> sendResetPasswordMail(@RequestBody String email) {
        authService.sendResetPasswordMail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ResetPasswordResponseDto> resetPassword(@RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
        return new ResponseEntity<>(authService.resetPassword(resetPasswordRequestDto), HttpStatus.OK);
    }
}
