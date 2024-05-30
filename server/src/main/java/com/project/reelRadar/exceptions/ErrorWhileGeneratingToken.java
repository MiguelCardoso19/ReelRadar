package com.project.reelRadar.exceptions;

import com.project.reelRadar.errors.ErrorMessage;

public class ErrorWhileGeneratingToken extends ReelRadarException{
    public ErrorWhileGeneratingToken(String message) {
        super(ErrorMessage.EMAIL_GENERATING_TOKEN);
    }
}