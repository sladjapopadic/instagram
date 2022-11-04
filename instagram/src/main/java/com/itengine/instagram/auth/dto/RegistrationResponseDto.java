package com.itengine.instagram.auth.dto;

import com.itengine.instagram.auth.enums.RegistrationResult;

public class RegistrationResponseDto {

    private RegistrationResult registrationResult;

    public RegistrationResponseDto(RegistrationResult registrationResult) {
        this.registrationResult = registrationResult;
    }

    public RegistrationResult getRegistrationResult() {
        return registrationResult;
    }

    public void setRegistrationResult(RegistrationResult registrationResult) {
        this.registrationResult = registrationResult;
    }
}
