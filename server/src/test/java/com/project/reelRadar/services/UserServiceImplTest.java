package com.project.reelRadar.services;

import com.project.reelRadar.dtos.RegisterRequestDTO;
import com.project.reelRadar.exceptions.UserAlreadyExistsException;
import com.project.reelRadar.models.User;
import com.project.reelRadar.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

  /*  @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private RegisterRequestDTO registerRequestDTO;


    @BeforeEach
    void setUp() {
        registerRequestDTO = new RegisterRequestDTO("testUsername", "testPassword", "testEmail@example.com");
    }

    @Test
    public void testSaveUserSuccessfully() throws UserAlreadyExistsException {

        when(userRepository.findByUsername(registerRequestDTO.username())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequestDTO.password())).thenReturn("encodedPassword");

        User savedUser = userService.save(registerRequestDTO);

        verify(userRepository, times(1)).findByUsername(registerRequestDTO.username());
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(registerRequestDTO.username(), savedUser.getUsername());
        assertEquals(registerRequestDTO.email(), savedUser.getEmail());
    }*/
}