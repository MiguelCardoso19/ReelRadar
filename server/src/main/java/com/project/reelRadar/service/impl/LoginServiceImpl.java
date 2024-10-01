package com.project.reelRadar.service.impl;

import com.project.reelRadar.dto.AuthenticationResponseDTO;
import com.project.reelRadar.dto.UserLoginRequestDTO;
import com.project.reelRadar.exception.customException.ErrorWhileAuth;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.UserRepository;
import com.project.reelRadar.service.LoginService;
import com.project.reelRadar.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponseDTO login(UserLoginRequestDTO userLoginRequestDTO) throws ErrorWhileAuth {
        User user = userRepository.findByUsername(userLoginRequestDTO.username())
                .orElseThrow(() -> new UsernameNotFoundException(userLoginRequestDTO.username()));

        if (!passwordEncoder.matches(userLoginRequestDTO.password(), user.getPassword())) {
            throw new ErrorWhileAuth();
        }

        String token = tokenService.generateToken(user);
        return new AuthenticationResponseDTO(user.getUsername(), user.getId(), token);
    }
}
