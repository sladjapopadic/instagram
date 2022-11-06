package com.itengine.instagram.auth.dto;

import com.itengine.instagram.auth.enums.ResetPasswordResult;

public class ResetPasswordResponseDto {

    private ResetPasswordResult resetPasswordResult;

    public ResetPasswordResponseDto(ResetPasswordResult resetPasswordResult) {
        this.resetPasswordResult = resetPasswordResult;
    }

    public ResetPasswordResult getResetPasswordResult() {
        return resetPasswordResult;
    }

    public void setResetPasswordResult(ResetPasswordResult resetPasswordResult) {
        this.resetPasswordResult = resetPasswordResult;
    }
}
