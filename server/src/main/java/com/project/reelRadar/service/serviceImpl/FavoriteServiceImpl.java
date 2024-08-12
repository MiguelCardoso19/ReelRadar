package com.project.reelRadar.service.serviceImpl;

import com.project.reelRadar.dto.FavoriteDTO;
import com.project.reelRadar.exception.FavoriteNotFoundException;
import com.project.reelRadar.model.Favorite;
import com.project.reelRadar.repository.FavoriteRepository;
import com.project.reelRadar.service.FavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;

    @Override
    public List<Favorite> getFavoritesByUserId(UUID userId) {
        return favoriteRepository.getFavoritesByUserId(userId);
    }

    @Override
    public Favorite save(FavoriteDTO favoriteDTO) {
        Favorite favorite = favoriteRepository.findById(favoriteDTO.Id()).orElse(new Favorite());

        if (favorite.getMovies() == null || !(favorite.getMovies() instanceof ArrayList)) {
            favorite.setMovies(new ArrayList<>(favorite.getMovies() != null ? favorite.getMovies() : new ArrayList<>()));
        }
        if (favorite.getTvShows() == null || !(favorite.getTvShows() instanceof ArrayList)) {
            favorite.setTvShows(new ArrayList<>(favorite.getTvShows() != null ? favorite.getTvShows() : new ArrayList<>()));
        }
        if (favorite.getPeople() == null || !(favorite.getPeople() instanceof ArrayList)) {
            favorite.setPeople(new ArrayList<>(favorite.getPeople() != null ? favorite.getPeople() : new ArrayList<>()));
        }

        if (favoriteDTO.movies() != null) {
            favorite.getMovies().addAll(List.of(favoriteDTO.movies().split(",")));
        }
        if (favoriteDTO.tv_shows() != null) {
            favorite.getTvShows().addAll(List.of(favoriteDTO.tv_shows().split(",")));
        }
        if (favoriteDTO.people() != null) {
            favorite.getPeople().addAll(List.of(favoriteDTO.people().split(",")));
        }

        return favoriteRepository.save(favorite);
    }

    @Override
    public void deleteFavorite(UUID favoriteId) throws FavoriteNotFoundException {
        Optional<Favorite> favorite = favoriteRepository.findById(favoriteId);
        if (favorite.isEmpty()) {
            throw new FavoriteNotFoundException();
        }
        favoriteRepository.delete(favorite.get());
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