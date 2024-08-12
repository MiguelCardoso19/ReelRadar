package com.project.reelRadar.service;

import com.project.reelRadar.dto.FavoriteDTO;
import com.project.reelRadar.exception.FavoriteNotFoundException;
import com.project.reelRadar.model.Favorite;

import java.util.List;
import java.util.UUID;

public interface FavoriteService {
    List<Favorite> getFavoritesByUserId(UUID userId);
    Favorite save(FavoriteDTO favorite);
    void deleteFavorite(UUID favoriteId) throws FavoriteNotFoundException;
}