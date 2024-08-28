package com.project.reelRadar.exception;

import com.project.reelRadar.exception.error.ErrorMessage;

public class InvalidFavoriteTypeException  extends ReelRadarException{
    public InvalidFavoriteTypeException(String type) {
        super(ErrorMessage.INVALID_FAVORITE_TYPE + type);
    }
}