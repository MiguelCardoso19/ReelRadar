package com.project.reelRadar.controllers;

import com.project.reelRadar.dtos.DeleteRequestDTO;
import com.project.reelRadar.exceptions.UserNotFoundException;
import com.project.reelRadar.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody DeleteRequestDTO deleteRequestDTO) throws UserNotFoundException {
        userService.delete(deleteRequestDTO);
        return ResponseEntity.ok().build();
    }
}