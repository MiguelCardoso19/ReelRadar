package com.project.reelRadar.service.impl;

import com.project.reelRadar.dto.UserDeleteRequestDTO;
import com.project.reelRadar.dto.UserDetailsResponseDTO;
import com.project.reelRadar.dto.UserRegisterRequestDTO;
import com.project.reelRadar.dto.UserUpdateRequestDTO;
import com.project.reelRadar.exception.customException.EmailAlreadyExistsException;
import com.project.reelRadar.exception.customException.UserAlreadyExistsException;
import com.project.reelRadar.exception.customException.UserNotFoundException;
import com.project.reelRadar.exception.customException.UsernameAlreadyExistsException;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.UserRepository;
import com.project.reelRadar.service.UserMapperService;
import com.project.reelRadar.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapperService userMapperService;

    @Override
    public User save(UserRegisterRequestDTO userRegisterRequestDTO) throws UserAlreadyExistsException,
            UsernameAlreadyExistsException, EmailAlreadyExistsException {
        Optional<User> existingUserByUsername = userRepository.findByUsername(userRegisterRequestDTO.username());
        Optional<User> existingUserByEmail = userRepository.findByEmail(userRegisterRequestDTO.email());

        if (existingUserByUsername.isPresent() && existingUserByEmail.isPresent()) {
            throw new UserAlreadyExistsException();
        } else if (existingUserByUsername.isPresent()) {
            throw new UsernameAlreadyExistsException();
        } else if (existingUserByEmail.isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        User newUser = userMapperService.UserRegisterDtoToUser(userRegisterRequestDTO);
        return userRepository.save(newUser);
    }


    @Override
    public void delete(UserDeleteRequestDTO userDeleteRequestDTO) throws UserNotFoundException {
        Optional<User> existUser = userRepository.findByUsername(userDeleteRequestDTO.username());
        if (existUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        userRepository.delete(existUser.get());
    }

    @Override
    public void update(String username, UserUpdateRequestDTO userUpdateRequestDTO) throws UserNotFoundException {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userMapperService.UserUpdateDtoToUser(userUpdateRequestDTO, existingUser.get());
        userRepository.save(user);
    }

    @Override
    public UserDetailsResponseDTO getUserDetails(String username) throws UserNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        return userMapperService.userToUserDetailsResponseDto(user);
    }

    public User getUser(UUID userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}