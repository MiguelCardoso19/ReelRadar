package com.project.reelRadar.service.impl;

import com.project.reelRadar.dto.AuthenticationResponseDTO;
import com.project.reelRadar.dto.UserLoginRequestDTO;
import com.project.reelRadar.dto.UserRegisterRequestDTO;
import com.project.reelRadar.exception.customException.EmailAlreadyExistsException;
import com.project.reelRadar.exception.customException.ErrorWhileAuth;
import com.project.reelRadar.exception.customException.UserAlreadyExistsException;
import com.project.reelRadar.exception.customException.UsernameAlreadyExistsException;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.UserRepository;
import com.project.reelRadar.service.AuthenticationService;
import com.project.reelRadar.service.TokenService;
import com.project.reelRadar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponseDTO login(UserLoginRequestDTO userLoginRequestDTO) throws ErrorWhileAuth {
        User user = userRepository.findByUsername(userLoginRequestDTO.username())
                .orElseThrow(() -> new UsernameNotFoundException(userLoginRequestDTO.username()));

        if (!passwordEncoder.matches(userLoginRequestDTO.password(), user.getPassword()))
            throw new ErrorWhileAuth();


        String token = tokenService.generateToken(user);
        return new AuthenticationResponseDTO(user.getUsername(), user.getId(), token);
    }

    @Override
    public AuthenticationResponseDTO register(UserRegisterRequestDTO userRegisterRequestDTO)
            throws UserAlreadyExistsException, ErrorWhileAuth,
            UsernameAlreadyExistsException, EmailAlreadyExistsException {

        User newUser = userService.save(userRegisterRequestDTO);

        if (newUser == null) throw new ErrorWhileAuth();

        String token = tokenService.generateToken(newUser);
        return new AuthenticationResponseDTO(newUser.getUsername(), newUser.getId(), token);
    }
}