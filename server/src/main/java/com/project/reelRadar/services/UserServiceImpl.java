package com.project.reelRadar.services;

import com.project.reelRadar.exceptions.UserAlreadyExistsException;
import com.project.reelRadar.models.User;
import com.project.reelRadar.repositories.UserRepository;
import com.project.reelRadar.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    @Override
    public User save(User user) throws UserAlreadyExistsException {
        Optional<User> existUser = userRepository.findByUsername(user.getUsername());
        if (existUser.isPresent()) {
            throw new UserAlreadyExistsException(existUser.get().getUsername());
        }
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(registerRequestDTO.password()));
        newUser.setUsername(registerRequestDTO.username());
        newUser.setEmail(registerRequestDTO.email());

        this.userRepository.save(newUser);

        String token = this.tokenService.generateToken(newUser);
    }
}
