package com.project.reelRadar.service.impl;

import com.project.reelRadar.dto.FavoriteDTO;
import com.project.reelRadar.dto.FavoriteDeleteRequestDTO;
import com.project.reelRadar.exception.customException.FavoriteNotFoundException;
import com.project.reelRadar.exception.customException.InvalidFavoriteTypeException;
import com.project.reelRadar.exception.customException.UserNotFoundException;
import com.project.reelRadar.model.Favorite;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.FavoriteRepository;
import com.project.reelRadar.service.FavoriteService;
import com.project.reelRadar.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserService userService;

    public Favorite save(FavoriteDTO favoriteDTO, UUID userId) throws UserNotFoundException {
        User user = userService.getUser(userId);

        if (user == null) throw new UserNotFoundException();

        Favorite favorite = favoriteRepository.findByUser(user).orElse(new Favorite());
        favorite.setUser(user);

        addItemsToFavorite(favorite, favoriteDTO);

        return favoriteRepository.save(favorite);
    }

    public List<FavoriteDTO> getFavoritesByUserId(UUID userId) {
        return favoriteRepository.getFavoritesByUserId(userId);
    }

    public void delete(UUID userId, FavoriteDeleteRequestDTO deleteRequest) throws FavoriteNotFoundException, UserNotFoundException, InvalidFavoriteTypeException {
        User user = userService.getUser(userId);

        Favorite favorite = favoriteRepository.findByUser(user).orElseThrow(FavoriteNotFoundException::new);

        switch (deleteRequest.type().toLowerCase()) {
            case "movies" -> favorite.setMovies(favorite.getMovies().stream()
                    .filter(movie -> !movie.equals(deleteRequest.value())).collect(Collectors.toList()));
            case "tv_shows" -> favorite.setTvShows(favorite.getTvShows().stream()
                    .filter(tvShow -> !tvShow.equals(deleteRequest.value())).collect(Collectors.toList()));
            case "people" -> favorite.setPeople(favorite.getPeople().stream()
                    .filter(person -> !person.equals(deleteRequest.value())).collect(Collectors.toList()));
            default -> throw new InvalidFavoriteTypeException(deleteRequest.type());
        }

        favoriteRepository.save(favorite);
    }

    private void addItemsToFavorite(Favorite favorite, FavoriteDTO favoriteDTO) {
        if (favorite.getMovies() == null) {
            favorite.setMovies(new ArrayList<>());
        }
        if (favorite.getTvShows() == null) {
            favorite.setTvShows(new ArrayList<>());
        }
        if (favorite.getPeople() == null) {
            favorite.setPeople(new ArrayList<>());
        }

        if (favoriteDTO.movies() != null && !favoriteDTO.movies().isEmpty()) {
            favorite.getMovies().addAll(favoriteDTO.movies());
        }

        if (favoriteDTO.tvShows() != null && !favoriteDTO.tvShows().isEmpty()) {
            favorite.getTvShows().addAll(favoriteDTO.tvShows());
        }

        if (favoriteDTO.people() != null && !favoriteDTO.people().isEmpty()) {
            favorite.getPeople().addAll(favoriteDTO.people());
        }
    }
}