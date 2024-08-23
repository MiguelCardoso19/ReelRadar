package com.project.reelRadar.service;

import com.project.reelRadar.dto.DeleteRequestDTO;
import com.project.reelRadar.dto.UserDetailsResponseDTO;
import com.project.reelRadar.dto.UserRegisterRequestDTO;
import com.project.reelRadar.dto.UserUpdateRequestDTO;
import com.project.reelRadar.exception.UserAlreadyExistsException;
import com.project.reelRadar.exception.UserNotFoundException;
import com.project.reelRadar.model.User;

public interface UserService {
    User save(UserRegisterRequestDTO userRegisterRequestDTO) throws UserAlreadyExistsException;
    void delete(DeleteRequestDTO deleteRequestDTO) throws UserNotFoundException;
    void update(String username, UserUpdateRequestDTO userUpdateRequestDTO) throws UserNotFoundException;
    UserDetailsResponseDTO getUserDetails(String username) throws UserNotFoundException;
}