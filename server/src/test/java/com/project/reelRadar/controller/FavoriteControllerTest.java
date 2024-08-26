package com.project.reelRadar.controller;
/**
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reelRadar.dto.FavoriteDTO;
import com.project.reelRadar.model.Favorite;
import com.project.reelRadar.service.FavoriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "userTest")
public class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FavoriteService favoriteService;

    private FavoriteDTO favoriteDTO;
    private UUID randomUUID;
    private List<Favorite> favoritesList;

    @BeforeEach
    void setUp() {
        randomUUID = UUID.randomUUID();

        favoriteDTO = new FavoriteDTO(
                UUID.randomUUID(),
                "movieTest",
                "peopleTest",
                "tv_showsTest");

        Favorite favorite = new Favorite();
        favorite.setPeople(new ArrayList<>());
        favorite.setMovies(new ArrayList<>());
        favorite.setTvShows(new ArrayList<>());
        favoritesList = Collections.singletonList(favorite);
    }

    @Test
    public void testSaveFavoriteSuccessfully() throws Exception {
        mockMvc.perform(post("/api/favorites/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(favoriteDTO)))
                .andExpect(status().isOk());

        verify(favoriteService, times(1)).save(Mockito.any(FavoriteDTO.class));
    }

    @Test
    public void testDeleteFavoriteSuccessfully() throws Exception {
        mockMvc.perform(delete("/api/favorites/remove/{favoriteId}", randomUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(favoriteService, times(1)).deleteFavorite(Mockito.eq(randomUUID));
    }

    @Test
    public void testGetFavoriteSuccessfully() throws Exception {

        when(favoriteService.getFavoritesByUserId(randomUUID)).thenReturn(favoritesList);

        mockMvc.perform(get("/api/favorites/show/{userId}", randomUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(favoriteService, times(1)).getFavoritesByUserId(Mockito.eq(randomUUID));
    }

    @Test
    public void testGetFavoriteNotSuccessfully() throws Exception {
        mockMvc.perform(get("/api/favorites/show/{userId}", randomUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(favoriteService, times(1)).getFavoritesByUserId(Mockito.eq(randomUUID));
    }
}*/