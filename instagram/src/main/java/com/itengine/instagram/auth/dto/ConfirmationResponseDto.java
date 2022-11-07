package com.itengine.instagram.auth.dto;

import com.itengine.instagram.auth.enums.ConfirmRegistrationResult;

public class ConfirmationResponseDto {

    private ConfirmRegistrationResult confirmRegistrationResult;
    private String token;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ConfirmationResponseDto(ConfirmRegistrationResult confirmRegistrationResult) {
        this.confirmRegistrationResult = confirmRegistrationResult;
    }

    public ConfirmationResponseDto(ConfirmRegistrationResult confirmRegistrationResult, String token, Long userId) {
        this.confirmRegistrationResult = confirmRegistrationResult;
        this.token = token;
        this.userId = userId;
    }

    public ConfirmRegistrationResult getConfirmRegistrationResult() {
        return confirmRegistrationResult;
    }

    public void setConfirmRegistrationResult(ConfirmRegistrationResult confirmRegistrationResult) {
        this.confirmRegistrationResult = confirmRegistrationResult;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
