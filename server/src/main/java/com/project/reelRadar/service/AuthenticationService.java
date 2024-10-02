package com.project.reelRadar.service;

import com.project.reelRadar.dto.AuthenticationResponseDTO;
import com.project.reelRadar.dto.UserLoginRequestDTO;
import com.project.reelRadar.dto.UserRegisterRequestDTO;
import com.project.reelRadar.exception.customException.EmailAlreadyExistsException;
import com.project.reelRadar.exception.customException.ErrorWhileAuth;
import com.project.reelRadar.exception.customException.UserAlreadyExistsException;
import com.project.reelRadar.exception.customException.UsernameAlreadyExistsException;

public interface AuthenticationService {
    AuthenticationResponseDTO login(UserLoginRequestDTO userLoginRequestDTO) throws ErrorWhileAuth;

    AuthenticationResponseDTO register(UserRegisterRequestDTO userRegisterRequestDTO)
            throws UserAlreadyExistsException, ErrorWhileAuth,
            UsernameAlreadyExistsException, EmailAlreadyExistsException;
}