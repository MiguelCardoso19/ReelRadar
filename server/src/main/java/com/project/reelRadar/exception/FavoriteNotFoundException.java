package com.project.reelRadar.exception;

import com.project.reelRadar.exception.error.ErrorMessage;

public class FavoriteNotFoundException extends ReelRadarException{
    public FavoriteNotFoundException() {
        super(ErrorMessage.FAVORITE_NOT_FOUND);
    }
}
