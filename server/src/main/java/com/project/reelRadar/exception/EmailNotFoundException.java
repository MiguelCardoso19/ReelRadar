package com.project.reelRadar.exception;

import com.project.reelRadar.exception.error.ErrorMessage;

public class EmailNotFoundException extends ReelRadarException{
    public EmailNotFoundException() {
        super(ErrorMessage.EMAIL_NOT_FOUND);
    }
}