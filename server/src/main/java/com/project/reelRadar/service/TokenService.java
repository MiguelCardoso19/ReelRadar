package com.project.reelRadar.service;

import com.project.reelRadar.exception.ErrorWhileAuth;
import com.project.reelRadar.model.User;

public interface TokenService {
    String generateToken(User user) throws ErrorWhileAuth;
    String validateToken(String token);
}