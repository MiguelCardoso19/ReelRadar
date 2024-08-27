package com.project.reelRadar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reelRadar.dto.FavoriteDTO;
import com.project.reelRadar.dto.FavoriteDeleteRequestDTO;
import com.project.reelRadar.service.FavoriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

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
    private FavoriteDeleteRequestDTO favoriteDeleteRequestDTO;
    private List<FavoriteDTO> favoritesListDTO;

    @BeforeEach
    void setUp() {
        randomUUID = UUID.randomUUID();

        favoritesListDTO = Collections.singletonList(new FavoriteDTO(
                List.of("movieTest1", "movieTest2"),
                List.of("peopleTest"),
                List.of("tvShowTest")
        ));

        favoriteDTO = new FavoriteDTO(
                new ArrayList<>(List.of("movieTest", "movieTest2")),
                new ArrayList<>(List.of("peopleTest")),
                new ArrayList<>(List.of("tv_showsTest"))
        );

        favoriteDeleteRequestDTO = new FavoriteDeleteRequestDTO(
                "movies", "movieTest");
    }

    @Test
    public void testSaveFavoriteSuccessfully() throws Exception {
        mockMvc.perform(post("/api/favorites/add/{userId}", randomUUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(favoriteDTO)))
                .andExpect(status().isOk());

        verify(favoriteService, times(1)).save(any(FavoriteDTO.class), eq(randomUUID));
    }

    @Test
    public void testDeleteFavoriteSuccessfully() throws Exception {
        mockMvc.perform(delete("/api/favorites/remove/{favoriteId}", randomUUID)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(favoriteDeleteRequestDTO)))
                .andExpect(status().isOk());

        verify(favoriteService, times(1)).delete(eq(randomUUID), any(FavoriteDeleteRequestDTO.class));
    }

    @Test
    public void testGetFavoriteSuccessfully() throws Exception {
        when(favoriteService.getFavoritesByUserId(randomUUID)).thenReturn(favoritesListDTO);

        mockMvc.perform(get("/api/favorites/show/{userId}", randomUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(favoriteService, times(1)).getFavoritesByUserId(eq(randomUUID));
    }
}