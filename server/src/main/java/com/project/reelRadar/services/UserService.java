package com.project.reelRadar.services;

import com.project.reelRadar.dtos.DeleteRequestDTO;
import com.project.reelRadar.dtos.LoginRequestDTO;
import com.project.reelRadar.dtos.RegisterRequestDTO;
import com.project.reelRadar.exceptions.UserAlreadyExistsException;
import com.project.reelRadar.exceptions.UserNotFoundException;
import com.project.reelRadar.models.User;

public interface UserService {
    User save(RegisterRequestDTO registerRequestDTO) throws UserAlreadyExistsException;
    void delete(DeleteRequestDTO deleteRequestDTO) throws UserNotFoundException;
}