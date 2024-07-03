package com.project.reelRadar.controllers;

import com.project.reelRadar.models.Favorite;
import com.project.reelRadar.repositories.FavoriteRepository;
import com.project.reelRadar.services.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping("/show/{userId}")
    public ResponseEntity<List<Object[]>> getMoviesPeopleTvShowsByUserId(@PathVariable UUID userId) {
        List<Object[]> data = favoriteService.getFavoritesByUserId(userId);
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }

    @PostMapping("/add")
    public ResponseEntity<Favorite> createFavorite(@RequestBody Favorite favorite) {
        Favorite createdFavorite = favoriteService.save(favorite);
        return ResponseEntity.ok(createdFavorite);
    }

    @DeleteMapping("/remove/{favoriteId}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable UUID favoriteId) {
        favoriteService.deleteFavorite(favoriteId);
        return ResponseEntity.noContent().build();
    }
}