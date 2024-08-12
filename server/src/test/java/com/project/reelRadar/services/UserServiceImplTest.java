package com.project.reelRadar.services;

import com.project.reelRadar.dto.DeleteRequestDTO;
import com.project.reelRadar.dto.RegisterRequestDTO;
import com.project.reelRadar.exception.UserAlreadyExistsException;
import com.project.reelRadar.exception.UserNotFoundException;
import com.project.reelRadar.model.Favorite;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.UserRepository;
import com.project.reelRadar.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private RegisterRequestDTO registerRequestDTO;

    private User user;


    @BeforeEach
    void setUp() {
        registerRequestDTO = new RegisterRequestDTO("testUsername", "testPassword", "testEmail@example.com");
        user = new User(UUID.randomUUID(),"TestUser","TestPassword", "TestEmail", new Favorite());
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
    }

    @Test
    public void deleteUserSuccessfully() throws UserNotFoundException {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        userService.delete(new DeleteRequestDTO(user.getUsername()));

        verify(userRepository, times(1)).delete(user);
    }
}