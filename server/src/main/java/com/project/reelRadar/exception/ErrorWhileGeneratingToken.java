package com.project.reelRadar.exception;

import com.project.reelRadar.exception.error.ErrorMessage;

public class ErrorWhileGeneratingToken extends ReelRadarException{
    public ErrorWhileGeneratingToken() {
        super(ErrorMessage.EMAIL_GENERATING_TOKEN);
    }
}