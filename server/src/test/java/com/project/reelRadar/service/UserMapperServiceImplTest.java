package com.project.reelRadar.service;

import com.project.reelRadar.dto.UserDetailsResponseDTO;
import com.project.reelRadar.dto.UserRegisterRequestDTO;
import com.project.reelRadar.dto.UserUpdateRequestDTO;
import com.project.reelRadar.model.User;
import com.project.reelRadar.service.impl.UserMapperServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UserMapperServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserMapperServiceImpl userMapperServiceImpl;

    private UserRegisterRequestDTO userRegisterRequestDTO;
    private UserUpdateRequestDTO userUpdateRequestDTO;
    private UserUpdateRequestDTO userUpdateRequestDTO_OnlyEmailFieldUpdate;
    private UserUpdateRequestDTO userUpdateRequestDTO_OnlyPasswordFieldUpdate;
    private UserUpdateRequestDTO userUpdateRequestDTO_OnlyUsernameFieldUpdate;
    private UserDetailsResponseDTO userDetailsResponseDTO;
    private User user;


    @BeforeEach
    void setUp() {
        openMocks(this);

        userRegisterRequestDTO = new UserRegisterRequestDTO(
                "username",
                "email@example.com",
                "encodedPassword");

        userUpdateRequestDTO = new UserUpdateRequestDTO(
                "updatedUsername",
                "updatedEmail@example.com",
                "newEncodedPassword");

        userUpdateRequestDTO_OnlyEmailFieldUpdate = new UserUpdateRequestDTO(
                null,
                "updatedEmail@example.com",
                null);

        userUpdateRequestDTO_OnlyPasswordFieldUpdate = new UserUpdateRequestDTO(
                null,
                null,
                "newEncodedPassword");

        userUpdateRequestDTO_OnlyUsernameFieldUpdate = new UserUpdateRequestDTO(
                "updatedUsername",
                null,
                null);

        userDetailsResponseDTO = new UserDetailsResponseDTO(
                "username",
                "email@example.com"
        );

        user = new User();
        user.setUsername("username");
        user.setEmail("email@example.com");
        user.setPassword("encodedPassword");
    }

    @Test
    void testUserRegisterDtoToUser() {
        when(passwordEncoder.encode("encodedPassword")).thenReturn("encodedPassword");

        User user = userMapperServiceImpl.UserRegisterDtoToUser(userRegisterRequestDTO);

        assertEquals(userRegisterRequestDTO.username(), user.getUsername());
        assertEquals(userRegisterRequestDTO.email(), user.getEmail());
        assertEquals(userRegisterRequestDTO.password(), user.getPassword());

        verify(passwordEncoder, times(1)).encode("encodedPassword");
    }


    @Test
    void testUserUpdateDtoToUser_WithAllFieldsUpdated() {
        when(passwordEncoder.encode("newEncodedPassword")).thenReturn("newEncodedPassword");

        User updatedUser = userMapperServiceImpl.UserUpdateDtoToUser(
                userUpdateRequestDTO, user);

        assertEquals(userUpdateRequestDTO.username(), updatedUser.getUsername());
        assertEquals(userUpdateRequestDTO.email(), updatedUser.getEmail());
        assertEquals(userUpdateRequestDTO.password(), updatedUser.getPassword());

        verify(passwordEncoder, times(1)).encode("newEncodedPassword");
    }

    @Test
    void testUserUpdateDtoToUser_OnlyEmailFieldUpdated() {
        User updatedUser = userMapperServiceImpl.UserUpdateDtoToUser(
                userUpdateRequestDTO_OnlyEmailFieldUpdate, user);

        assertEquals(user.getUsername(), updatedUser.getUsername());
        assertEquals(userUpdateRequestDTO_OnlyEmailFieldUpdate.email(), updatedUser.getEmail());
        assertEquals(user.getPassword(), updatedUser.getPassword());

        verify(passwordEncoder, times(0)).encode(anyString());
    }

    @Test
    void testUserUpdateDtoToUser_OnlyUsernameFieldUpdated() {
        User updatedUser = userMapperServiceImpl.UserUpdateDtoToUser(
                userUpdateRequestDTO_OnlyUsernameFieldUpdate, user);

        assertEquals(userUpdateRequestDTO_OnlyUsernameFieldUpdate.username(), updatedUser.getUsername());
        assertEquals(user.getEmail(), updatedUser.getEmail());
        assertEquals(user.getPassword(), updatedUser.getPassword());

        verify(passwordEncoder, times(0)).encode(anyString());
    }

    @Test
    void testUserUpdateDtoToUser_OnlyPasswordFieldUpdated() {
        when(passwordEncoder.encode("newEncodedPassword")).thenReturn("newEncodedPassword");

        User updatedUser = userMapperServiceImpl.UserUpdateDtoToUser(
                userUpdateRequestDTO_OnlyPasswordFieldUpdate, user);

        assertEquals(user.getUsername(), updatedUser.getUsername());
        assertEquals(user.getEmail(), updatedUser.getEmail());
        assertEquals(userUpdateRequestDTO_OnlyPasswordFieldUpdate.password(), updatedUser.getPassword());

        verify(passwordEncoder, times(1)).encode("newEncodedPassword");
    }

    @Test
    void testUserToUserDetailsResponseDto() {
        userMapperServiceImpl.userToUserDetailsResponseDto(Optional.ofNullable(user));

        assertEquals(user.getUsername(), userDetailsResponseDTO.username());
        assertEquals(user.getEmail(), userDetailsResponseDTO.email());
    }
}