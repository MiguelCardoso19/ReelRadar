package com.project.reelRadar.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.reelRadar.exception.ErrorWhileAuth;
import com.project.reelRadar.exception.UserNotFoundException;
import com.project.reelRadar.model.Token;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.TokenRepository;
import com.project.reelRadar.repository.UserRepository;
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
    private final UserRepository userRepository;

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

            saveUserToken(user, token);

            return token;

        } catch (JWTCreationException exception) {
            throw new ErrorWhileAuth();
        }
    }

    public String validateToken(String token) {
        try {            Algorithm algorithm = Algorithm.HMAC256(secret);

            String jwtToken = JWT.require(algorithm)
                    .withIssuer("ReelRadar-API")
                    .build()
                    .verify(token)
                    .getSubject();

            User user = userRepository.findByUsername(jwtToken)
                    .orElseThrow(UserNotFoundException::new);

            saveUserToken(user, token);

            return jwtToken;

        } catch (JWTVerificationException exception) {
            System.err.println("JWT verification failed: " + exception.getMessage());
            return null;
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
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

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.of("+01:00"));
    }

    private Instant generateInstantDate() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("+01:00"));
    }
}
