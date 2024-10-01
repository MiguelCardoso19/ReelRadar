package com.project.reelRadar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reelRadar.dto.UserLoginRequestDTO;
import com.project.reelRadar.dto.UserRegisterRequestDTO;
import com.project.reelRadar.model.Token;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.TokenRepository;
import com.project.reelRadar.repository.UserRepository;
import com.project.reelRadar.service.impl.TokenServiceImpl;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenServiceImpl tokenService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private TokenRepository tokenRepository;

    private UserRegisterRequestDTO userRegisterRequestDTO;
    private UserLoginRequestDTO userLoginRequestDTO;
    private User user;
    private String encodedPassword;
    private Token storedToken;
    private String validToken;


    @BeforeEach
    void setUp() {
        encodedPassword = new BCryptPasswordEncoder().encode("passwordTest");

        user = new User();
        user.setUsername("userTest");
        user.setPassword(encodedPassword);
        user.setEmail("emailTest@gmail.com");
        user.setId(UUID.randomUUID());

        userRegisterRequestDTO = new UserRegisterRequestDTO(
                "registerRequestDTOTest",
                "emailTest@gmail.com",
                "passwordTest"
        );

        userLoginRequestDTO = new UserLoginRequestDTO(
                "userTest",
                "passwordTest");

        validToken = "validTestToken";

        storedToken = new Token();
        storedToken.setToken(validToken);
        storedToken.setExpired(false);
        storedToken.setRevoked(false);
    }

    @Test
    public void testRegisterSuccessfully() throws Exception {
        when(userService.save(Mockito.any(UserRegisterRequestDTO.class))).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn("testToken");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("userTest"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Id").value(user.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("testToken"));


        verify(tokenService, times(1)).generateToken(user);
        verify(userService, times(1)).save(Mockito.any(UserRegisterRequestDTO.class));
    }

    @Test
    public void testRegisterNotSuccessfully() throws Exception {
        when(userService.save(any(UserRegisterRequestDTO.class))).thenReturn(null);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterRequestDTO)))
                .andExpect(status().isBadRequest());

        verify(userService, times(1)).save(any(UserRegisterRequestDTO.class));
    }

    @Test
    public void testLoginSuccessfully() throws Exception {
        when(userRepository.findByUsername("userTest")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("passwordTest", encodedPassword)).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn("testToken");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("userTest"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("testToken"));

        verify(userRepository, times(1)).findByUsername("userTest");
        verify(passwordEncoder, times(1)).matches("passwordTest", encodedPassword);
        verify(tokenService, times(1)).generateToken(user);
    }

    @Test
    public void testLoginNotSuccessfully() throws Exception {
        when(userRepository.findByUsername("userTest")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("passwordTest", encodedPassword)).thenReturn(false);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginRequestDTO)))
                .andExpect(status().isBadRequest());

        verify(userRepository, times(1)).findByUsername("userTest");
        verify(passwordEncoder, times(1)).matches("passwordTest", encodedPassword);
    }

    @Test
    public void testLogoutSuccessfully() throws Exception {
        when(tokenRepository.findByToken(validToken)).thenReturn(Optional.of(storedToken));

        mockMvc.perform(get("/api/auth/logout")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(tokenRepository, times(1)).findByToken(validToken);
        verify(tokenRepository, times(1)).save(storedToken);

        assertTrue(storedToken.isExpired());
        assertTrue(storedToken.isRevoked());
    }

    @Test
    public void testLogoutWithoutAuthorizationHeader() throws Exception {
        mockMvc.perform(get("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}