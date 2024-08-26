package com.project.reelRadar.service.serviceImpl;

import com.project.reelRadar.dto.FavoriteDTO;
import com.project.reelRadar.dto.FavoriteDeleteRequestDTO;
import com.project.reelRadar.exception.FavoriteNotFoundException;
import com.project.reelRadar.exception.UserNotFoundException;
import com.project.reelRadar.model.Favorite;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.FavoriteRepository;
import com.project.reelRadar.repository.UserRepository;
import com.project.reelRadar.service.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;

    public Favorite save(FavoriteDTO favoriteDTO, UUID userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userOptional.get();

        Favorite favorite = favoriteRepository.findByUser(user).orElse(new Favorite());
        favorite.setUser(user);

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

        return favoriteRepository.save(favorite);
    }

    public List<FavoriteDTO> getFavoritesByUserId(UUID userId) {
        return favoriteRepository.getFavoritesByUserId(userId);
    }


    public void delete(UUID userId, FavoriteDeleteRequestDTO deleteRequest) throws UserNotFoundException, FavoriteNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Favorite favorite = favoriteRepository.findByUser(user).orElseThrow(FavoriteNotFoundException::new);

        switch (deleteRequest.type().toLowerCase()) {
            case "movies":
                favorite.setMovies(favorite.getMovies().stream().filter(movie -> !movie.equals(deleteRequest.value())).collect(Collectors.toList()));
                break;
            case "tv_shows":
                favorite.setTvShows(favorite.getTvShows().stream().filter(tvShow -> !tvShow.equals(deleteRequest.value())).collect(Collectors.toList()));
                break;
            case "people":
                favorite.setPeople(favorite.getPeople().stream().filter(person -> !person.equals(deleteRequest.value())).collect(Collectors.toList()));
                break;
            default:
                throw new IllegalArgumentException("Invalid type: " + deleteRequest.type());
        }

        favoriteRepository.save(favorite);
    }
}