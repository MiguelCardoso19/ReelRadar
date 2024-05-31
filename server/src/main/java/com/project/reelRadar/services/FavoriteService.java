package com.project.reelRadar.services;

import com.project.reelRadar.models.Favorite;

public interface FavoriteService {
    Favorite findByUserId(String userId);
    Favorite save(Favorite favorite);
    void deleteFavorite(String favoriteId);
}