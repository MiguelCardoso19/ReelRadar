package com.project.reelRadar.exception.customException;

import com.project.reelRadar.exception.error.ErrorMessage;

public class UsernameAlreadyExistsException  extends ReelRadarException{
    public UsernameAlreadyExistsException() {
        super(ErrorMessage.USERNAME_ALREADY_EXISTS);
    }
}
