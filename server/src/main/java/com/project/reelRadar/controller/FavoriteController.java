package com.project.reelRadar.controller;

import com.project.reelRadar.dto.FavoriteDTO;
import com.project.reelRadar.dto.FavoriteDeleteRequestDTO;
import com.project.reelRadar.exception.FavoriteNotFoundException;
import com.project.reelRadar.exception.InvalidFavoriteTypeException;
import com.project.reelRadar.exception.UserNotFoundException;
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
            summary = "Show User Favorites",
            description = "Retrieves a list of movies, TV shows, or people favorited by the specified user. Returns HTTP status 200 OK if successful."
    )
    @GetMapping("/show/{userId}")
    public ResponseEntity<List<FavoriteDTO>> getFavoritesByUserId(@PathVariable UUID userId) {
        List<FavoriteDTO> favorites = favoriteService.getFavoritesByUserId(userId);
        return ResponseEntity.ok(favorites);
    }

    @Operation(
            summary = "Add Favorite Item",
            description = "Adds a new favorite movie, TV show, or person for the user. Returns HTTP status 200 OK if successful."
    )
    @PostMapping("/add/{userId}")
    public ResponseEntity<Void> createFavorite(@RequestBody FavoriteDTO favoriteDTO, @PathVariable UUID userId) throws UserNotFoundException {
        favoriteService.save(favoriteDTO, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Delete Favorite Item",
            description = "Deletes a specific favorite item (movie, TV show, or person) for the user. Provide the favorite type and value in the request body. Returns HTTP status 200 OK if successful."
    )
    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<Void> deleteFavoriteItem(@PathVariable UUID userId, @RequestBody FavoriteDeleteRequestDTO deleteRequest) throws FavoriteNotFoundException, UserNotFoundException, InvalidFavoriteTypeException {
        favoriteService.delete(userId, deleteRequest);
        return ResponseEntity.ok().build();
    }
}