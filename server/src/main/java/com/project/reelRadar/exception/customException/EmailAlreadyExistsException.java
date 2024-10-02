package com.project.reelRadar.exception.customException;

import com.project.reelRadar.exception.error.ErrorMessage;

public class EmailAlreadyExistsException extends ReelRadarException {
    public EmailAlreadyExistsException() {
        super(ErrorMessage.EMAIL_ALREADY_EXISTS);
    }
}