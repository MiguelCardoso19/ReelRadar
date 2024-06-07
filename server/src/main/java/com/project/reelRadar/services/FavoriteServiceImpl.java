package com.project.reelRadar.services;
/**
import com.project.reelRadar.models.Favorite;
import com.project.reelRadar.repositories.FavoriteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

 @Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;

    @Override
    public Favorite findByUserId(String userId) {
        return favoriteRepository.findByUserId(userId);
    }

    @Override
    public Favorite save(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    @Override
    public void deleteFavorite(String favoriteId) {
        favoriteRepository.deleteById(UUID.fromString(favoriteId));
    }
} */