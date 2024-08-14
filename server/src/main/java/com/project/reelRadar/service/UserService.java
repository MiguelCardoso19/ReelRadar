package com.project.reelRadar.service;

import com.project.reelRadar.dto.DeleteRequestDTO;
import com.project.reelRadar.dto.RegisterRequestDTO;
import com.project.reelRadar.dto.UpdateRequestDTO;
import com.project.reelRadar.exception.UserAlreadyExistsException;
import com.project.reelRadar.exception.UserNotFoundException;
import com.project.reelRadar.model.User;

public interface UserService {
    User save(RegisterRequestDTO registerRequestDTO) throws UserAlreadyExistsException;
    void delete(DeleteRequestDTO deleteRequestDTO) throws UserNotFoundException;
    void update(UpdateRequestDTO updateRequestDTO) throws UserNotFoundException;
}