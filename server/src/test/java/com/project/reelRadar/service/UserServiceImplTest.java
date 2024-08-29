package com.project.reelRadar.service;

import com.project.reelRadar.dto.UserDeleteRequestDTO;
import com.project.reelRadar.dto.UserDetailsResponseDTO;
import com.project.reelRadar.dto.UserRegisterRequestDTO;
import com.project.reelRadar.dto.UserUpdateRequestDTO;
import com.project.reelRadar.exception.UserAlreadyExistsException;
import com.project.reelRadar.exception.UserNotFoundException;
import com.project.reelRadar.model.Favorite;
import com.project.reelRadar.model.Token;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.UserRepository;
import com.project.reelRadar.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
                "testEmail@example.com", (List<Token>) new Token(), new Favorite());
    }

    @Test
    public void testSaveUserSuccessfully() throws UserAlreadyExistsException {
        when(userRepository.findByUsername(userRegisterRequestDTO.username())).thenReturn(Optional.empty());
        when(userMapperService.UserRegisterDtoToUser(any(UserRegisterRequestDTO.class))).thenReturn(user);

        User savedUser = userService.save(userRegisterRequestDTO);

        verify(userRepository, times(1)).findByUsername(userRegisterRequestDTO.username());
        verify(userMapperService, times(1)).UserRegisterDtoToUser(any(UserRegisterRequestDTO.class));
        verify(userRepository, times(1)).save(any(User.class));

        assertEquals(userRegisterRequestDTO.username(), savedUser.getUsername());
        assertEquals(userRegisterRequestDTO.email(), savedUser.getEmail());
        assertEquals(userRegisterRequestDTO.password(), user.getPassword());
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
        when(userMapperService.userToUserDetailsResponseDto(Optional.ofNullable(user))).thenReturn(userDetailsResponseDTO);

        UserDetailsResponseDTO result = userService.getUserDetails(user.getUsername());

        assertEquals(userDetailsResponseDTO, result);
        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userMapperService, times(1)).userToUserDetailsResponseDto(Optional.ofNullable(user));
    }

    @Test
    public void getUserSuccessfully() throws UserNotFoundException {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User result = userService.getUser(user.getId());

        assertEquals(user, result);
        verify(userRepository, times(1)).findById(user.getId());
    }
}