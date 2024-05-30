package com.project.reelRadar.services;

import com.project.reelRadar.models.Favorite;

public interface FavoriteService {
    Favorite findByUserId(String userId);
    Favorite saveFavorite(Favorite favorite);
    Favorite updateFavorite(Favorite favorite);
    void deleteFavorite(String favoriteId);
}