package com.project.reelRadar.service;

import com.project.reelRadar.dto.FavoriteDTO;
import com.project.reelRadar.dto.FavoriteDeleteRequestDTO;
import com.project.reelRadar.exception.FavoriteNotFoundException;
import com.project.reelRadar.exception.UserNotFoundException;
import com.project.reelRadar.model.Favorite;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.FavoriteRepository;
import com.project.reelRadar.service.serviceImpl.FavoriteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class FavoriteServiceImplTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    @Mock
    private UserService userService;

    private FavoriteDTO favoriteDTO;
    private Favorite favorite;
    private FavoriteDeleteRequestDTO favoriteDeleteRequestDTO;
    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        openMocks(this);

        user = new User();
        user.setId(userId = UUID.randomUUID());

        favorite = new Favorite();
        favorite.setUser(user);
        favorite.setMovies(new ArrayList<>(List.of("MovieTest1", "MovieTest2")));
        favorite.setPeople(new ArrayList<>());
        favorite.setTvShows(new ArrayList<>(List.of()));

        favoriteDTO = new FavoriteDTO(
                new ArrayList<>(List.of("NewMovie1", "NewMovie2")),
                new ArrayList<>(List.of("NewPerson1")),
                new ArrayList<>(List.of("NewTvShow1"))
        );

        favoriteDeleteRequestDTO = new FavoriteDeleteRequestDTO(
                "movies", "MovieTest1");
    }

    @Test
    public void testSaveFavoriteSuccessfully() throws UserNotFoundException {
        when(userService.getUser(userId)).thenReturn(user);
        when(favoriteRepository.findByUser(user)).thenReturn(Optional.of(favorite));
        when(favoriteRepository.save(any(Favorite.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Favorite savedFavorite = favoriteService.save(favoriteDTO, userId);

        verify(userService, times(1)).getUser(userId);
        verify(favoriteRepository, times(1)).findByUser(user);
        verify(favoriteRepository, times(1)).save(any(Favorite.class));

        assertEquals(4, savedFavorite.getMovies().size());
        assertTrue(savedFavorite.getMovies().contains("NewMovie1"));
        assertTrue(savedFavorite.getMovies().contains("NewMovie2"));

        assertEquals(1, savedFavorite.getTvShows().size());
        assertEquals("NewTvShow1", savedFavorite.getTvShows().getFirst());

        assertEquals(1, savedFavorite.getPeople().size());
        assertEquals("NewPerson1", savedFavorite.getPeople().getFirst());
    }

    @Test
    public void deleteFavoriteSuccessfully() throws UserNotFoundException, FavoriteNotFoundException {
        when(userService.getUser(userId)).thenReturn(user);
        when(favoriteRepository.findByUser(user)).thenReturn(Optional.of(favorite));

        favoriteService.delete(userId, favoriteDeleteRequestDTO);

        verify(favoriteRepository, times(1)).save(favorite);

        assertFalse(favorite.getMovies().contains("MovieTest1"));
        assertTrue(favorite.getMovies().contains("MovieTest2"));
    }
}