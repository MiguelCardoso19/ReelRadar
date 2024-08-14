package com.project.reelRadar.controller;

import com.project.reelRadar.dto.FavoriteDTO;
import com.project.reelRadar.exception.FavoriteNotFoundException;
import com.project.reelRadar.model.Favorite;
import com.project.reelRadar.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@Tag(name = "Favorite")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @Operation(
            summary = "Get User Favorites",
            description = "Retrieves a list of movies, TV shows, or people favorited by the specified user. If no favorites are found, a 204 No Content response is returned."
    )
    @GetMapping("/show/{userId}")
    public ResponseEntity<List<Favorite>> getMoviesPeopleTvShowsByUserId(@PathVariable UUID userId) {
        List<Favorite> data = favoriteService.getFavoritesByUserId(userId);
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }

    @Operation(
            summary = "Add Favorite",
            description = "Adds a new favorite movie, TV show, or person for the user. The created favorite item is returned in the response."
    )
    @PostMapping("/add")
    public ResponseEntity<Favorite> createFavorite(@RequestBody FavoriteDTO favorite) {
        Favorite createdFavorite = favoriteService.save(favorite);
        return ResponseEntity.ok(createdFavorite);
    }

    @Operation(
            summary = "Remove Favorite",
            description = "Removes a favorite movie, TV show, or person by its ID. If the favorite item is not found, an exception is thrown."
    )
    @DeleteMapping("/remove/{favoriteId}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable UUID favoriteId) throws FavoriteNotFoundException {
        favoriteService.deleteFavorite(favoriteId);
        return ResponseEntity.noContent().build();
    }
}