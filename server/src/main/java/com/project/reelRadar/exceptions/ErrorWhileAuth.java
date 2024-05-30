package com.project.reelRadar.exceptions;

import com.project.reelRadar.errors.ErrorMessage;

public class ErrorWhileAuth extends ReelRadarException{
    public ErrorWhileAuth(String message) {
        super(ErrorMessage.ERROR_WHILE_AUTH);
    }
}