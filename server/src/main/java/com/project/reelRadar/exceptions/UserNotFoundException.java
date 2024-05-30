package com.project.reelRadar.exceptions;

import com.project.reelRadar.errors.ErrorMessage;

public class UserNotFoundException extends ReelRadarException {

    public UserNotFoundException(String message) {
        super(ErrorMessage.USER_NOT_FOUND);
    }
}