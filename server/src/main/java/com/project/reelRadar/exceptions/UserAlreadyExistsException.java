package com.project.reelRadar.exceptions;

import com.project.reelRadar.errors.ErrorMessage;

public class UserAlreadyExistsException extends ReelRadarException {

    public UserAlreadyExistsException(String message) {
        super(ErrorMessage.USER_ALREADY_EXISTS);
    }
}