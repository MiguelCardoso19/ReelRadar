package com.project.reelRadar.service.impl;

import com.project.reelRadar.dto.AuthenticationResponseDTO;
import com.project.reelRadar.dto.UserRegisterRequestDTO;
import com.project.reelRadar.exception.customException.ErrorWhileAuth;
import com.project.reelRadar.exception.customException.UserAlreadyExistsException;
import com.project.reelRadar.model.User;
import com.project.reelRadar.service.RegistrationService;
import com.project.reelRadar.service.TokenService;
import com.project.reelRadar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final UserService userService;
    private final TokenService tokenService;

    @Override
    public AuthenticationResponseDTO registerUser(UserRegisterRequestDTO userRegisterRequestDTO) throws UserAlreadyExistsException, ErrorWhileAuth {
        User newUser = userService.save(userRegisterRequestDTO);
        if (newUser == null) {
            throw new ErrorWhileAuth();
        }

        String token = tokenService.generateToken(newUser);
        return new AuthenticationResponseDTO(newUser.getUsername(), newUser.getId(), token);
    }
}