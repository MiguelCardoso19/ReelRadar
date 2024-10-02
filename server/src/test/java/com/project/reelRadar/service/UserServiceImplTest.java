package com.project.reelRadar.service;

import com.project.reelRadar.dto.UserDeleteRequestDTO;
import com.project.reelRadar.dto.UserDetailsResponseDTO;
import com.project.reelRadar.dto.UserRegisterRequestDTO;
import com.project.reelRadar.dto.UserUpdateRequestDTO;
import com.project.reelRadar.exception.customException.EmailAlreadyExistsException;
import com.project.reelRadar.exception.customException.UserAlreadyExistsException;
import com.project.reelRadar.exception.customException.UserNotFoundException;
import com.project.reelRadar.exception.customException.UsernameAlreadyExistsException;
import com.project.reelRadar.model.Favorite;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.UserRepository;
import com.project.reelRadar.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import static org.mockito.MockitoAnnotations.openMocks;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapperService userMapperService;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRegisterRequestDTO userRegisterRequestDTO;
    private UserUpdateRequestDTO userUpdateRequestDTO;
    private UserDetailsResponseDTO userDetailsResponseDTO;
    private User user;

    @BeforeEach
    void setUp() {
        openMocks(this);

        userDetailsResponseDTO = new UserDetailsResponseDTO(
                "testUsername",
                "testEmail@example.com");

        userRegisterRequestDTO = new UserRegisterRequestDTO(
                "testUsername",
                "testEmail@example.com",
                "TestPassword");

        userUpdateRequestDTO = new UserUpdateRequestDTO(
                "testUsername",
                "testEmail@example.com",
                "TestPassword");

        user = new User(UUID.randomUUID(),
                "testUsername",
                "TestPassword",
                "testEmail@example.com", new ArrayList<>(), new Favorite());
    }

    @Test
    public void testSaveUserSuccessfully() throws UserAlreadyExistsException, UsernameAlreadyExistsException, EmailAlreadyExistsException {
        when(userRepository.findByUsername(userRegisterRequestDTO.username())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userRegisterRequestDTO.email())).thenReturn(Optional.empty()); // Add this line
        when(userMapperService.UserRegisterDtoToUser(any(UserRegisterRequestDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user); // Mock save return value

        User savedUser = userService.save(userRegisterRequestDTO);

        verify(userRepository, times(1)).findByUsername(userRegisterRequestDTO.username());
        verify(userRepository, times(1)).findByEmail(userRegisterRequestDTO.email()); // Verify email check
        verify(userMapperService, times(1)).UserRegisterDtoToUser(any(UserRegisterRequestDTO.class));
        verify(userRepository, times(1)).save(any(User.class));

        assertEquals(userRegisterRequestDTO.username(), savedUser.getUsername());
        assertEquals(userRegisterRequestDTO.email(), savedUser.getEmail());
        assertEquals(userRegisterRequestDTO.password(), savedUser.getPassword()); // Fix the source of password
    }

    @Test
    public void deleteUserSuccessfully() throws UserNotFoundException {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        userService.delete(new UserDeleteRequestDTO(user.getUsername()));

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testUpdateUserSuccessfully() throws UserNotFoundException {
        when(userRepository.findByUsername(userUpdateRequestDTO.username())).thenReturn(Optional.of(user));
        when(userMapperService.UserUpdateDtoToUser(any(UserUpdateRequestDTO.class), any(User.class))).thenReturn(user);

        userService.update(userUpdateRequestDTO.username(), userUpdateRequestDTO);

        verify(userRepository, times(1)).findByUsername(userUpdateRequestDTO.username());
        verify(userMapperService, times(1)).UserUpdateDtoToUser(any(UserUpdateRequestDTO.class), any(User.class));
        verify(userRepository, times(1)).save(user);

        assertEquals(userUpdateRequestDTO.username(), user.getUsername());
        assertEquals(userUpdateRequestDTO.email(), user.getEmail());
        assertEquals(userUpdateRequestDTO.password(), user.getPassword());
    }

    @Test
    public void getUserDetailsSuccessfully() throws UserNotFoundException {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(userMapperService.userToUserDetailsResponseDto(any())).thenReturn(userDetailsResponseDTO);

        UserDetailsResponseDTO result = userService.getUserDetails(user.getUsername());

        assertEquals(userDetailsResponseDTO, result);
        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userMapperService, times(1)).userToUserDetailsResponseDto(any());
    }

    @Test
    public void getUserSuccessfully() throws UserNotFoundException {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User result = userService.getUser(user.getId());

        assertEquals(user, result);
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    public void testSaveUserUsernameAlreadyExists() {
        when(userRepository.findByUsername(userRegisterRequestDTO.username())).thenReturn(Optional.of(user));

        assertThrows(UsernameAlreadyExistsException.class, () -> {
            userService.save(userRegisterRequestDTO);
        });

        verify(userRepository, times(1)).findByUsername(userRegisterRequestDTO.username());
    }

    @Test
    public void testSaveUserEmailAlreadyExists() {
        when(userRepository.findByUsername(userRegisterRequestDTO.username())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userRegisterRequestDTO.email())).thenReturn(Optional.of(user));

        assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.save(userRegisterRequestDTO);
        });

        verify(userRepository, times(1)).findByUsername(userRegisterRequestDTO.username());
        verify(userRepository, times(1)).findByEmail(userRegisterRequestDTO.email());
    }

    @Test
    public void testDeleteUserNotFound() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.delete(new UserDeleteRequestDTO(user.getUsername()));
        });

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userRepository, never()).delete(any());
    }

    @Test
    public void testUpdateUserNotFound() {
        when(userRepository.findByUsername(userUpdateRequestDTO.username())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.update(userUpdateRequestDTO.username(), userUpdateRequestDTO);
        });

        verify(userRepository, times(1)).findByUsername(userUpdateRequestDTO.username());
        verify(userRepository, never()).save(any());
    }

    @Test
    public void testGetUserDetailsNotFound() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserDetails(user.getUsername());
        });

        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    public void testGetUserNotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUser(user.getId());
        });

        verify(userRepository, times(1)).findById(user.getId());
    }
}