package com.project.reelRadar.services;

import com.project.reelRadar.exceptions.UserAlreadyExistsException;
import com.project.reelRadar.models.User;

public interface UserService {
    User save(String password, String username, String email) throws UserAlreadyExistsException;
}
