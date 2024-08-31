package com.project.reelRadar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reelRadar.dto.UserDeleteRequestDTO;
import com.project.reelRadar.dto.UserDetailsResponseDTO;
import com.project.reelRadar.dto.UserUpdateRequestDTO;
import com.project.reelRadar.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "userTest")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserDeleteRequestDTO userDeleteRequestDTO;
    private UserUpdateRequestDTO userUpdateRequestDTO;
    private UserDetailsResponseDTO userDetailsResponseDTO;

    @BeforeEach
    void setUp() {
        userDeleteRequestDTO = new UserDeleteRequestDTO("TestUser");

        userUpdateRequestDTO = new UserUpdateRequestDTO(
                "username",
                "example@email.com",
                "encodedPassword");

        userDetailsResponseDTO = new UserDetailsResponseDTO(
                "username",
                "example@email.com");
    }

    @Test
    public void testDeleteUserSuccessfully() throws Exception {
        mockMvc.perform(delete("/api/user/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDeleteRequestDTO)))
                .andExpect(status().isOk());

        verify(userService, times(1)).delete(any(UserDeleteRequestDTO.class));
    }

    @Test
    public void testUpdateUserSuccessfully() throws Exception {
        mockMvc.perform(put("/api/user/update/{username}", "username")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateRequestDTO)))
                .andExpect(status().isOk());

        verify(userService, times(1)).update(anyString(), any(UserUpdateRequestDTO.class));
    }

    @Test
    public void testGetUserSuccessfully() throws Exception {
        when(userService.getUserDetails(anyString())).thenReturn(userDetailsResponseDTO);

        mockMvc.perform(get("/api/user/details/{username}", "username")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userDetailsResponseDTO.username()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userDetailsResponseDTO.email()));

        verify(userService, times(1)).getUserDetails(anyString());
    }
}