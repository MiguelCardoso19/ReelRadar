package com.project.reelRadar.services;

import com.project.reelRadar.dtos.RegisterRequestDTO;
import com.project.reelRadar.exceptions.UserAlreadyExistsException;
import com.project.reelRadar.models.User;
import com.project.reelRadar.repositories.UserRepository;
import com.project.reelRadar.security.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(String password, String username, String email) throws UserAlreadyExistsException {
        Optional<User> existUser = userRepository.findByUsername(username);
        if (existUser.isPresent()) {
            throw new UserAlreadyExistsException(existUser.get().getUsername());
        }
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setUsername(username);
        newUser.setEmail(email);

        this.userRepository.save(newUser);

        return newUser;
    }
}
