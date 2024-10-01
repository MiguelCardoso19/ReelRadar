package com.project.reelRadar.service;

import com.project.reelRadar.dto.UserDeleteRequestDTO;
import com.project.reelRadar.dto.UserDetailsResponseDTO;
import com.project.reelRadar.dto.UserRegisterRequestDTO;
import com.project.reelRadar.dto.UserUpdateRequestDTO;
import com.project.reelRadar.exception.customException.UserAlreadyExistsException;
import com.project.reelRadar.exception.customException.UserNotFoundException;
import com.project.reelRadar.model.User;

import java.util.UUID;

public interface UserService {
    User save(UserRegisterRequestDTO userRegisterRequestDTO) throws UserAlreadyExistsException;
    void delete(UserDeleteRequestDTO userDeleteRequestDTO) throws UserNotFoundException;
    void update(String username, UserUpdateRequestDTO userUpdateRequestDTO) throws UserNotFoundException;
    UserDetailsResponseDTO getUserDetails(String username) throws UserNotFoundException;
    User getUser(UUID userId) throws UserNotFoundException;
}