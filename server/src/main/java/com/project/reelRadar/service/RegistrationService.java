package com.project.reelRadar.service;

import com.project.reelRadar.dto.AuthenticationResponseDTO;
import com.project.reelRadar.dto.UserRegisterRequestDTO;
import com.project.reelRadar.exception.customException.ErrorWhileAuth;
import com.project.reelRadar.exception.customException.UserAlreadyExistsException;

public interface RegistrationService {
    AuthenticationResponseDTO registerUser(UserRegisterRequestDTO userRegisterRequestDTO)
            throws UserAlreadyExistsException, ErrorWhileAuth;
}
