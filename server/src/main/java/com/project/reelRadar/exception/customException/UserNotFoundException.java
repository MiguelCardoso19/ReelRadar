package com.project.reelRadar.exception.customException;

import com.project.reelRadar.exception.error.ErrorMessage;

public class UserNotFoundException extends ReelRadarException {
    public UserNotFoundException() {
        super(ErrorMessage.USER_NOT_FOUND);
    }
}