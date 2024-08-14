package com.project.reelRadar.service.serviceImpl;

import com.project.reelRadar.dto.DeleteRequestDTO;
import com.project.reelRadar.dto.RegisterRequestDTO;
import com.project.reelRadar.dto.UpdateRequestDTO;
import com.project.reelRadar.exception.UserAlreadyExistsException;
import com.project.reelRadar.exception.UserNotFoundException;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.UserRepository;
import com.project.reelRadar.service.UserService;
import lombok.AllArgsConstructor;
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

        throw new UserAlreadyExistsException();
    }

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) throws UserNotFoundException {
        Optional<User> existUser = userRepository.findByUsername(deleteRequestDTO.username());
        if (!existUser.isPresent()) {
            throw new UserNotFoundException();
        }
        userRepository.delete(existUser.get());
    }

    @Override
    public void update(UpdateRequestDTO updateRequestDTO) throws UserNotFoundException {
        Optional<User> existUser = userRepository.findByUsername(updateRequestDTO.username());
        if (!existUser.isPresent()) {
            throw new UserNotFoundException();
        }
        userRepository.save(existUser.get());
    }
}