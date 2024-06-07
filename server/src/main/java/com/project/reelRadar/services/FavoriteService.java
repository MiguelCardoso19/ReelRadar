package com.project.reelRadar.services;

import com.project.reelRadar.models.Favorite;

import java.util.List;
import java.util.UUID;

public interface FavoriteService {
    List<Object[]> getFavoritesByUserId(UUID userId);
    Favorite save(Favorite favorite);
    void deleteFavorite(UUID favoriteId);
}