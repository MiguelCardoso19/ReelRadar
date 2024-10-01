package com.project.reelRadar.exception.customException;

import com.project.reelRadar.exception.error.ErrorMessage;

public class ErrorWhileGeneratingToken extends ReelRadarException{
    public ErrorWhileGeneratingToken() {
        super(ErrorMessage.ERROR_WHILE_GENERATING_TOKEN);
    }
}