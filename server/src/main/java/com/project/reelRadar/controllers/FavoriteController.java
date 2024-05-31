package com.project.reelRadar.controllers;

import com.project.reelRadar.models.Favorite;
import com.project.reelRadar.services.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;


    @GetMapping("/{userId}")
    public ResponseEntity<Favorite> getFavoriteByUserId(@PathVariable String userId) {
        Favorite favorite = favoriteService.findByUserId(userId);
        if (favorite == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(favorite);
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<Favorite> createFavorite(@RequestBody Favorite favorite) {
        Favorite createdFavorite = favoriteService.save(favorite);
        return ResponseEntity.ok(createdFavorite);
    }

    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<Void> deleteFavorite(@PathVariable String favoriteId) {
        favoriteService.deleteFavorite(favoriteId);
        return ResponseEntity.noContent().build();
    }
}