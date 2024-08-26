package com.project.reelRadar.service.serviceImpl;

import com.project.reelRadar.dto.UserDeleteRequestDTO;
import com.project.reelRadar.dto.UserDetailsResponseDTO;
import com.project.reelRadar.dto.UserRegisterRequestDTO;
import com.project.reelRadar.dto.UserUpdateRequestDTO;
import com.project.reelRadar.exception.UserAlreadyExistsException;
import com.project.reelRadar.exception.UserNotFoundException;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.UserRepository;
import com.project.reelRadar.service.UserMapperService;
import com.project.reelRadar.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapperService userMapperService;

    @Override
    public User save(UserRegisterRequestDTO userRegisterRequestDTO) throws UserAlreadyExistsException {
        Optional<User> existUser = userRepository.findByUsername(userRegisterRequestDTO.username());
        if (existUser.isEmpty()) {
            User newUser = userMapperService.UserRegisterDtoToUser(userRegisterRequestDTO);
            this.userRepository.save(newUser);

            return newUser;
        }

        throw new UserAlreadyExistsException();
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
}