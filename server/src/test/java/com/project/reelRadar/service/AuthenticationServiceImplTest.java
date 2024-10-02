package com.project.reelRadar.service;

import com.project.reelRadar.dto.AuthenticationResponseDTO;
import com.project.reelRadar.dto.UserLoginRequestDTO;
import com.project.reelRadar.dto.UserRegisterRequestDTO;
import com.project.reelRadar.exception.customException.EmailAlreadyExistsException;
import com.project.reelRadar.exception.customException.ErrorWhileAuth;
import com.project.reelRadar.exception.customException.UserAlreadyExistsException;
import com.project.reelRadar.exception.customException.UsernameAlreadyExistsException;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.UserRepository;
import com.project.reelRadar.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.MockitoAnnotations.openMocks;

public class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private UserRegisterRequestDTO userRegisterRequestDTO;
    private User newUser;
    private User user;
    private String validPassword = "validPassword";
    private String encodedPassword;

    @BeforeEach
    void setUp() {
        openMocks(this);

        user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("userTest");
        user.setPassword(validPassword);
        encodedPassword = "encodedPassword";

        userRegisterRequestDTO = new UserRegisterRequestDTO(
                "username", "email@example.com", "password");

        newUser = new User();
        newUser.setId(UUID.randomUUID());
        newUser.setUsername("username");
    }

    @Test
    public void testLoginSuccess() throws ErrorWhileAuth {
        UserLoginRequestDTO loginRequest = new UserLoginRequestDTO("userTest", validPassword);

        when(userRepository.findByUsername("userTest")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(validPassword, user.getPassword())).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn("testToken");

        AuthenticationResponseDTO response = authenticationService.login(loginRequest);

        assertEquals("userTest", response.username());
        assertEquals(user.getId(), response.Id());
        assertEquals("testToken", response.token());

        verify(userRepository, times(1)).findByUsername("userTest");
        verify(passwordEncoder, times(1)).matches(validPassword, user.getPassword());
        verify(tokenService, times(1)).generateToken(user);
    }

    @Test
    public void testLoginUserNotFound() throws ErrorWhileAuth {
        UserLoginRequestDTO loginRequest = new UserLoginRequestDTO("userTest", validPassword);

        when(userRepository.findByUsername("userTest")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authenticationService.login(loginRequest));

        verify(userRepository, times(1)).findByUsername("userTest");
        verify(passwordEncoder, never()).matches(any(), any());
        verify(tokenService, never()).generateToken(any());
    }

    @Test
    public void testLoginIncorrectPassword() throws ErrorWhileAuth {
        UserLoginRequestDTO loginRequest = new UserLoginRequestDTO("userTest", "wrongPassword");

        when(userRepository.findByUsername("userTest")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", user.getPassword())).thenReturn(false);

        assertThrows(ErrorWhileAuth.class, () -> authenticationService.login(loginRequest));

        verify(userRepository, times(1)).findByUsername("userTest");
        verify(passwordEncoder, times(1)).matches("wrongPassword", user.getPassword());
        verify(tokenService, never()).generateToken(any());
    }

    @Test
    public void testRegisterUserSuccessfully() throws UserAlreadyExistsException, ErrorWhileAuth, UsernameAlreadyExistsException, EmailAlreadyExistsException {
        when(userService.save(userRegisterRequestDTO)).thenReturn(newUser);
        when(tokenService.generateToken(newUser)).thenReturn("testToken");

        AuthenticationResponseDTO response = authenticationService.register(userRegisterRequestDTO);

        assertEquals("username", response.username());
        assertEquals(newUser.getId(), response.Id());
        assertEquals("testToken", response.token());

        verify(userService, times(1)).save(userRegisterRequestDTO);
        verify(tokenService, times(1)).generateToken(newUser);
    }

    @Test
    public void testRegisterUserAlreadyExists() throws UserAlreadyExistsException, UsernameAlreadyExistsException, ErrorWhileAuth, EmailAlreadyExistsException {
        when(userService.save(userRegisterRequestDTO)).thenThrow(new UserAlreadyExistsException());

        assertThrows(UserAlreadyExistsException.class, () -> authenticationService.register(userRegisterRequestDTO));

        verify(userService, times(1)).save(userRegisterRequestDTO);
        verify(tokenService, never()).generateToken(any());
    }

    @Test
    public void testRegisterUserErrorWhileAuth() throws UserAlreadyExistsException, UsernameAlreadyExistsException, EmailAlreadyExistsException, ErrorWhileAuth {
        when(userService.save(userRegisterRequestDTO)).thenReturn(null);

        assertThrows(ErrorWhileAuth.class, () -> authenticationService.register(userRegisterRequestDTO));

        verify(userService, times(1)).save(userRegisterRequestDTO);
        verify(tokenService, never()).generateToken(any());
    }
}
