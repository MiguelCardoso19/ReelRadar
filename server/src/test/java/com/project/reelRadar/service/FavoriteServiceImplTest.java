package com.project.reelRadar.service;

import com.project.reelRadar.dto.FavoriteDTO;
import com.project.reelRadar.exception.FavoriteNotFoundException;
import com.project.reelRadar.model.Favorite;
import com.project.reelRadar.repository.FavoriteRepository;
import com.project.reelRadar.service.serviceImpl.FavoriteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceImplTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    private FavoriteDTO favoriteDTO;
    private Favorite favorite;

    @BeforeEach
    void setUp() {
        favorite = new Favorite();
        favorite.setId(UUID.randomUUID());
        favorite.setMovies(new ArrayList<>());
        favorite.setPeople(new ArrayList<>());
        favorite.setTvShows(new ArrayList<>());

        favoriteDTO = new FavoriteDTO(
                UUID.randomUUID(),
                "movieTest1,movieTest2",
                "peopleTest1,peopleTest2",
                "tv_showTest1,tv_showTest2"
        );
    }

    @Test
    public void testSaveFavoriteSuccessfully() {
        when(favoriteRepository.findById(favoriteDTO.Id())).thenReturn(Optional.of(favorite));
        when(favoriteRepository.save(any(Favorite.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Favorite savedFavorite = favoriteService.save(favoriteDTO);

        verify(favoriteRepository, times(1)).findById(favoriteDTO.Id());
        verify(favoriteRepository, times(1)).save(any(Favorite.class));

        assertEquals(2, savedFavorite.getMovies().size());
        assertEquals("movieTest1", savedFavorite.getMovies().get(0));
        assertEquals("movieTest2", savedFavorite.getMovies().get(1));

        assertEquals(2, savedFavorite.getPeople().size());
        assertEquals("peopleTest1", savedFavorite.getPeople().get(0));
        assertEquals("peopleTest2", savedFavorite.getPeople().get(1));

        assertEquals(2, savedFavorite.getTvShows().size());
        assertEquals("tv_showTest1", savedFavorite.getTvShows().get(0));
        assertEquals("tv_showTest2", savedFavorite.getTvShows().get(1));
    }

    @Test
    public void deleteFavoriteSuccessfully() throws FavoriteNotFoundException {
        when(favoriteRepository.findById(favorite.getId())).thenReturn(Optional.of(favorite));

        favoriteService.deleteFavorite(favorite.getId());

        verify(favoriteRepository, times(1)).delete(favorite);
    }
}
