package com.project.reelRadar.exception;

import com.project.reelRadar.exception.error.ErrorMessage;

public class FavoriteAlreadyExists extends ReelRadarException{
    public FavoriteAlreadyExists() {
        super(ErrorMessage.FAVORITE_ALREADY_EXISTS);
    }
}