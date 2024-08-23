package com.project.reelRadar.controller;

import com.project.reelRadar.dto.DeleteRequestDTO;
import com.project.reelRadar.dto.UserDetailsResponseDTO;
import com.project.reelRadar.dto.UserUpdateRequestDTO;
import com.project.reelRadar.exception.UserNotFoundException;
import com.project.reelRadar.model.Favorite;
import com.project.reelRadar.model.User;
import com.project.reelRadar.repository.UserRepository;
import com.project.reelRadar.service.UserService;
import com.project.reelRadar.service.serviceImpl.UserMapperServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Delete User",
            description = "Deletes a user based on the provided details in the request body. If the user is not found, a `UserNotFoundException` is thrown."
    )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody DeleteRequestDTO deleteRequestDTO) throws UserNotFoundException {
        userService.delete(deleteRequestDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Update User",
            description = "Updates a user based on the provided details in the request body. If the user is not found, a `UserNotFoundException` is thrown."
    )
    @PutMapping("/update/{username}")
    public ResponseEntity<Void> update(@PathVariable String username, @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) throws UserNotFoundException {
        userService.update(username, userUpdateRequestDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Get User Details",
            description = "Retrieves the details of a user based on the provided username. If the user is not found, a `UserNotFoundException` is thrown."
    )
    @GetMapping("/details/{username}")
    public ResponseEntity<UserDetailsResponseDTO> getUserDetails(@PathVariable String username) throws UserNotFoundException {
        UserDetailsResponseDTO userDetailsResponseDTO = userService.getUserDetails(username);
        return ResponseEntity.ok(userDetailsResponseDTO);
    }
}