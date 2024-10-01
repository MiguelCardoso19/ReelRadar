package com.project.reelRadar.service;

import com.project.reelRadar.dto.AuthenticationResponseDTO;
import com.project.reelRadar.dto.UserLoginRequestDTO;
import com.project.reelRadar.exception.customException.ErrorWhileAuth;

public interface LoginService {
    AuthenticationResponseDTO login(UserLoginRequestDTO userLoginRequestDTO) throws ErrorWhileAuth;
}