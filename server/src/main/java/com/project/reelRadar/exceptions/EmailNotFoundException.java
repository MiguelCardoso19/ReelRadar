package com.project.reelRadar.exceptions;

import com.project.reelRadar.errors.ErrorMessage;

public class EmailNotFoundException extends ReelRadarException{
    public EmailNotFoundException(String message) {
        super(ErrorMessage.EMAIL_NOT_FOUND);
    }
}