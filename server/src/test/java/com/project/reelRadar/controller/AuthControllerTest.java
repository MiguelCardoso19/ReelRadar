package com.project.reelRadar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reelRadar.dto.LoginRequestDTO;
import com.project.reelRadar.dto.RegisterRequestDTO;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.UserRepository;
import com.project.reelRadar.security.TokenService;
import com.project.reelRadar.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private RegisterRequestDTO registerRequestDTO;
    private LoginRequestDTO loginRequestDTO;
    private User user;
    private String encodedPassword;


    @BeforeEach
    void setUp() {
        encodedPassword = new BCryptPasswordEncoder().encode("passwordTest");

        user = new User();
        user.setUsername("userTest");
        user.setPassword(encodedPassword);
        user.setEmail("emailTest@gmail.com");
        user.setId(UUID.randomUUID());

        registerRequestDTO = new RegisterRequestDTO(
                "registerRequestDTOTest",
                "emailTest@gmail.com",
                "passwordTest"
        );

        loginRequestDTO = new LoginRequestDTO(
                "userTest",
                "passwordTest");
    }

    @Test
    public void testRegisterSucessfully() throws Exception {
        when(userService.save(Mockito.any(RegisterRequestDTO.class))).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn("testToken");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("userTest"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Id").value(user.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("testToken"));


        verify(tokenService, times(1)).generateToken(user);
        verify(userService, times(1)).save(Mockito.any(RegisterRequestDTO.class));
    }

    @Test
    public void testRegisterNotSucessfully() throws Exception {
        when(userService.save(any(RegisterRequestDTO.class)))
                .thenReturn(null);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequestDTO)))
                .andExpect(status().isBadRequest());

        verify(userService, times(1)).save(any(RegisterRequestDTO.class));
    }

    @Test
    public void testLoginSuccessfully() throws Exception {
        when(userRepository.findByUsername("userTest")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("passwordTest", encodedPassword)).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn("testToken");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("userTest"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("testToken"));

        verify(userRepository, times(1)).findByUsername("userTest");
        verify(passwordEncoder, times(1)).matches("passwordTest", encodedPassword);
        verify(tokenService, times(1)).generateToken(user);
    }

    @Test
    public void testLoginNotSucessfully() throws Exception {
        when(userRepository.findByUsername("userTest")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("passwordTest", encodedPassword)).thenReturn(false);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andExpect(status().isBadRequest());

        verify(userRepository, times(1)).findByUsername("userTest");
        verify(passwordEncoder, times(1)).matches("passwordTest", encodedPassword);
    }
}