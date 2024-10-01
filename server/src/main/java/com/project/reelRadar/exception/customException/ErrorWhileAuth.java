package com.project.reelRadar.exception.customException;

import com.project.reelRadar.exception.error.ErrorMessage;

public class ErrorWhileAuth extends ReelRadarException{
    public ErrorWhileAuth() {
        super(ErrorMessage.ERROR_WHILE_AUTH);
    }
}