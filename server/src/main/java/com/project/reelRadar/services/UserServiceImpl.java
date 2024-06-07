package com.project.reelRadar.services;

import com.project.reelRadar.dtos.DeleteRequestDTO;
import com.project.reelRadar.dtos.LoginRequestDTO;
import com.project.reelRadar.dtos.RegisterRequestDTO;
import com.project.reelRadar.exceptions.UserAlreadyExistsException;
import com.project.reelRadar.exceptions.UserNotFoundException;
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
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(RegisterRequestDTO registerRequestDTO) throws UserAlreadyExistsException {
        Optional<User> existUser = userRepository.findByUsername(registerRequestDTO.username());
        if (existUser.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(registerRequestDTO.password()));
            newUser.setUsername(registerRequestDTO.username());
            newUser.setEmail(registerRequestDTO.email());

            this.userRepository.save(newUser);
            return newUser;
        }

        throw new UserAlreadyExistsException(existUser.get().getUsername());
    }

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) throws UserNotFoundException {
        Optional<User> existUser = userRepository.findByUsername(deleteRequestDTO.username());
        if (!existUser.isPresent()) {
            throw new UserNotFoundException("User with ID " + deleteRequestDTO.username() + " not found.");
        }
        userRepository.delete(existUser.get());
    }
}