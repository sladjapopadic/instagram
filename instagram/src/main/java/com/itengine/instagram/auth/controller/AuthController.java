package com.itengine.instagram.auth.controller;

import com.itengine.instagram.auth.dto.*;
import com.itengine.instagram.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDto> register(@RequestBody RegistrationRequestDto registrationRequestDto) {
        return new ResponseEntity<>(authService.register(registrationRequestDto), HttpStatus.OK);
    }

    @PatchMapping("/confirm")
    public ResponseEntity<ConfirmationResponseDto> confirmRegistration(@RequestBody ConfirmationRequestDto confirmationRequestDto) {
        return new ResponseEntity<>(authService.confirmRegistration(confirmationRequestDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<Void> sendResetPasswordMail(@RequestBody String email) {
        authService.sendResetPasswordMail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        authService.resetPassword(resetPasswordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
