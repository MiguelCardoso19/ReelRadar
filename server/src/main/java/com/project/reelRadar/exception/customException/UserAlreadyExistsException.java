package com.project.reelRadar.exception.customException;

import com.project.reelRadar.exception.error.ErrorMessage;

public class UserAlreadyExistsException extends ReelRadarException {
    public UserAlreadyExistsException() {
        super(ErrorMessage.USER_ALREADY_EXISTS);
    }
}