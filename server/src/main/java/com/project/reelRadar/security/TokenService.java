package com.project.reelRadar.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.project.reelRadar.exception.ErrorWhileAuth;
import com.project.reelRadar.model.Token;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) throws ErrorWhileAuth {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("ReelRadar-API")
                    .withIssuedAt(generateInstantDate())
                    .withSubject(user.getUsername())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);

            revokeAllUserTokens(user);
            saveUserToken(user, token);

            return token;

        } catch (JWTCreationException exception) {
            throw new ErrorWhileAuth();
        }
    }

    public String validateToken(String token) {
        try {            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("ReelRadar-API")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (RuntimeException e) {
            System.err.println("JWT verification failed: " + e.getMessage());
            return null;
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.of("+01:00"));
    }

    private Instant generateInstantDate() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("+01:00"));
    }
}