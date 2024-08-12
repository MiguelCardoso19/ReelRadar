package com.project.reelRadar.controller;

import com.project.reelRadar.dto.FavoriteDTO;
import com.project.reelRadar.exception.FavoriteNotFoundException;
import com.project.reelRadar.model.Favorite;
import com.project.reelRadar.service.FavoriteService;
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
    public ResponseEntity<List<Favorite>> getMoviesPeopleTvShowsByUserId(@PathVariable UUID userId) {
        List<Favorite> data = favoriteService.getFavoritesByUserId(userId);
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }

    @PostMapping("/add")
    public ResponseEntity<Favorite> createFavorite(@RequestBody FavoriteDTO favorite) {
        Favorite createdFavorite = favoriteService.save(favorite);
        return ResponseEntity.ok(createdFavorite);
    }

    @DeleteMapping("/remove/{favoriteId}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable UUID favoriteId) throws FavoriteNotFoundException {
        favoriteService.deleteFavorite(favoriteId);
        return ResponseEntity.noContent().build();
    }
}