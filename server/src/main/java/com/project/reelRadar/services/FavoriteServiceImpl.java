package com.project.reelRadar.services;

import com.project.reelRadar.models.Favorite;
import com.project.reelRadar.repositories.FavoriteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;

    @Override
    public Favorite findByUserId(String userId) {
        return favoriteRepository.findByUserId(userId);
    }

    @Override
    public Favorite saveFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    @Override
    public Favorite updateFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite); // Save method works for both create and update
    }

    @Override
    public void deleteFavorite(String favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }
}