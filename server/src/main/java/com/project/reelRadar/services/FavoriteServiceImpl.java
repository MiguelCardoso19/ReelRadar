package com.project.reelRadar.services;

import com.project.reelRadar.dtos.FavoriteDTO;
import com.project.reelRadar.models.Favorite;
import com.project.reelRadar.repositories.FavoriteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;

    @Override
    public List<Object[]> getFavoritesByUserId(UUID userId) {
        return favoriteRepository.getFavoritesByUserId(userId);
    }

    @Override
    public Favorite save(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    @Override
    public void deleteFavorite(UUID favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }

     public List<Map<String, String>> convertToMap(List<FavoriteDTO> favorites) {
         List<Map<String, String>> response = new ArrayList<>();
         for (FavoriteDTO favorite : favorites) {
             Map<String, String> entry = new HashMap<>();
             entry.put("tv_shows", favorite.tv_shows());
             entry.put("movies", favorite.movies());
             entry.put("people", favorite.people());
             response.add(entry);
         }
         return response;
     }

 }