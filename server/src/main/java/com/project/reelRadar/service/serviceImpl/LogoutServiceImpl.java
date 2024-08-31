package com.project.reelRadar.service.serviceImpl;

import com.project.reelRadar.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutHandler {
    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        var authHeader = request.getHeader("Authorization");
        final String jwtToken;

        if (authHeader == null) return;

        jwtToken = authHeader.replace("Bearer ", "");
        var storedToken = tokenRepository.findByToken(jwtToken).orElse(null);

        if (storedToken != null) {
            storedToken.setRevoked(true);
            storedToken.setExpired(true);
            tokenRepository.save(storedToken);
        }
    }
}