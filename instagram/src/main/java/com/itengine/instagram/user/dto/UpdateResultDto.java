package com.itengine.instagram.user.dto;

import com.itengine.instagram.user.enums.UpdateResult;

public class UpdateResultDto {

    private UpdateResult updateResult;

    public UpdateResultDto(UpdateResult updateResult) {
        this.updateResult = updateResult;
    }

    public UpdateResult getUpdateResult() {
        return updateResult;
    }

    public void setUpdateResult(UpdateResult updateResult) {
        this.updateResult = updateResult;
    }
}
