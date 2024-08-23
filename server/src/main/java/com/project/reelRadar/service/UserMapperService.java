package com.project.reelRadar.service;

import com.project.reelRadar.dto.UserDetailsResponseDTO;
import com.project.reelRadar.dto.UserRegisterRequestDTO;
import com.project.reelRadar.dto.UserUpdateRequestDTO;
import com.project.reelRadar.model.User;

import java.util.Optional;

public interface UserMapperService {
    User UserRegisterDtoToUser(UserRegisterRequestDTO userRegisterRequestDTO);
    User UserUpdateDtoToUser(UserUpdateRequestDTO userUpdateRequestDTO, User user);
    UserDetailsResponseDTO UserToUserDetailsResponseDto(Optional<User> user);
}
