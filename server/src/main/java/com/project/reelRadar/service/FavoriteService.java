package com.project.reelRadar.service;

import com.project.reelRadar.dto.FavoriteDTO;
import com.project.reelRadar.dto.FavoriteDeleteRequestDTO;
import com.project.reelRadar.exception.FavoriteNotFoundException;
import com.project.reelRadar.exception.InvalidFavoriteTypeException;
import com.project.reelRadar.exception.UserNotFoundException;
import com.project.reelRadar.model.Favorite;

import java.util.List;
import java.util.UUID;

public interface FavoriteService {
    List<FavoriteDTO> getFavoritesByUserId(UUID userId);
    Favorite save(FavoriteDTO favorite, UUID userId) throws UserNotFoundException;
    void delete(UUID userId, FavoriteDeleteRequestDTO deleteRequest) throws FavoriteNotFoundException, UserNotFoundException, InvalidFavoriteTypeException;
}