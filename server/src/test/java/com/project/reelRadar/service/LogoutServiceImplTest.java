package com.project.reelRadar.service;

import com.project.reelRadar.model.Token;
import com.project.reelRadar.repository.TokenRepository;
import com.project.reelRadar.service.serviceImpl.LogoutServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class LogoutServiceImplTest {

    private LogoutServiceImpl logoutService;
    private TokenRepository tokenRepository;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Authentication authentication;
    private String validToken;
    private String invalidToken;
    private Token storedToken;

    @BeforeEach
    public void setUp() {
        tokenRepository = mock(TokenRepository.class);
        logoutService = new LogoutServiceImpl(tokenRepository);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        authentication = mock(Authentication.class);

        validToken = "validToken";
        invalidToken = "invalidToken";

        storedToken = new Token();
        storedToken.setToken(validToken);
        storedToken.setExpired(false);
        storedToken.setRevoked(false);
    }

    @Test
    public void testLogoutSuccessfully() {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validToken);
        when(tokenRepository.findByToken(validToken)).thenReturn(Optional.of(storedToken));

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, times(1)).findByToken(validToken);
        verify(tokenRepository, times(1)).save(storedToken);

        assertTrue(storedToken.isExpired());
        assertTrue(storedToken.isRevoked());

        verify(response, never()).setStatus(anyInt());
    }

    @Test
    public void testLogoutWithoutAuthorizationHeader() {
        when(request.getHeader("Authorization")).thenReturn(null);

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, never()).findByToken(anyString());
        verify(tokenRepository, never()).save(any());
        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    public void testLogoutWithInvalidToken() {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + invalidToken);
        when(tokenRepository.findByToken(invalidToken)).thenReturn(Optional.empty());

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, times(1)).findByToken(invalidToken);
        verify(tokenRepository, never()).save(any());
        verify(response, never()).setStatus(anyInt());
    }
}