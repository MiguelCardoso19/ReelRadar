package com.project.reelRadar.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.project.reelRadar.exception.ErrorWhileAuth;
import com.project.reelRadar.model.Token;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.TokenRepository;
import com.project.reelRadar.service.serviceImpl.TokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class TokenServiceImplTest {

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private User user;

    private String secret;
    private String token;
    private String invalidToken;
    private String oldToken;
    private UUID userId;
    private Token existingToken;

    @BeforeEach
    public void setUp() throws IllegalAccessException, NoSuchFieldException {
        openMocks(this);

        oldToken = "oldToken";
        userId = UUID.randomUUID();
        invalidToken = "invalidToken";
        secret = "testSecret";

        var secretField = TokenServiceImpl.class.getDeclaredField("secret");
        secretField.setAccessible(true);
        secretField.set(tokenService, secret);

        token = JWT.create()
                .withIssuer("ReelRadar-API")
                .withSubject("testUser")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now()
                        .plusSeconds(3600))
                .sign(Algorithm.HMAC256(secret));

        existingToken = Token.builder()
                .user(user)
                .token(oldToken)
                .expired(false)
                .revoked(false)
                .build();
    }

    @Test
    public void testGenerateTokenSuccessfully() throws ErrorWhileAuth {
        when(user.getUsername()).thenReturn("testUser");
        when(tokenRepository.findAllValidTokenByUser(any())).thenReturn(Collections.emptyList());
        when(tokenRepository.save(any(Token.class))).thenReturn(null);

        String generatedToken = tokenService.generateToken(user);

        assertNotNull(generatedToken);
        assertTrue(generatedToken.startsWith("eyJ"));

        verify(tokenRepository, times(1)).save(any(Token.class));
        verify(tokenRepository, times(1)).findAllValidTokenByUser(user.getId());
    }

    @Test
    public void testValidateTokenSuccessfully() {
        String subject = tokenService.validateToken(token);

        assertEquals("testUser", subject);
    }

    @Test
    public void testValidateTokenInvalid() {
        String subject = tokenService.validateToken(invalidToken);

        assertNull(subject);
    }

    @Test
    public void testGenerateTokenRevokesPreviousTokens() throws ErrorWhileAuth {
        when(user.getUsername()).thenReturn("testUser");
        when(user.getId()).thenReturn(userId);

        when(tokenRepository.findAllValidTokenByUser(userId)).thenReturn(Collections.singletonList(existingToken));

        String newToken = tokenService.generateToken(user);

        assertTrue(existingToken.isExpired());
        assertTrue(existingToken.isRevoked());

        verify(tokenRepository).saveAll(Collections.singletonList(existingToken));

        verify(tokenRepository).save(argThat(token -> token.getToken().equals(newToken) && !token.isExpired() && !token.isRevoked()));
    }
}