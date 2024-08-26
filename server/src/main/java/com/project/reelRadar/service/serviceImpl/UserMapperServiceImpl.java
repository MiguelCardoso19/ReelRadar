package com.project.reelRadar.service.serviceImpl;

import com.project.reelRadar.dto.UserDetailsResponseDTO;
import com.project.reelRadar.dto.UserRegisterRequestDTO;
import com.project.reelRadar.dto.UserUpdateRequestDTO;
import com.project.reelRadar.model.User;
import com.project.reelRadar.service.UserMapperService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserMapperServiceImpl implements UserMapperService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User UserRegisterDtoToUser(UserRegisterRequestDTO userRegisterRequestDTO) {
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(userRegisterRequestDTO.password()));
        newUser.setUsername(userRegisterRequestDTO.username());
        newUser.setEmail(userRegisterRequestDTO.email());

        return newUser;
    }

    @Override
    public User UserUpdateDtoToUser(UserUpdateRequestDTO userUpdateRequestDTO, User user) {

        if (userUpdateRequestDTO.email() != null) {
            user.setEmail(userUpdateRequestDTO.email());
        }
        if (userUpdateRequestDTO.username() != null) {
            user.setUsername(userUpdateRequestDTO.username());
        }

        if (userUpdateRequestDTO.password() != null && !userUpdateRequestDTO.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userUpdateRequestDTO.password()));
        }

        return user;
    }

    @Override
    public UserDetailsResponseDTO userToUserDetailsResponseDto(Optional<User> user) {

        return new UserDetailsResponseDTO(
                user.get().getUsername(),
                user.get().getEmail());
    }
}