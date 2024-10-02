package com.project.reelRadar.controller;

import com.project.reelRadar.exception.customException.EmailAlreadyExistsException;
import com.project.reelRadar.exception.customException.ErrorWhileAuth;
import com.project.reelRadar.exception.customException.UserAlreadyExistsException;
import com.project.reelRadar.dto.UserLoginRequestDTO;
import com.project.reelRadar.dto.UserRegisterRequestDTO;
import com.project.reelRadar.dto.AuthenticationResponseDTO;
import com.project.reelRadar.exception.customException.UsernameAlreadyExistsException;
import com.project.reelRadar.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(
            summary = "User Login",
            description = "Authenticates a user with their username and password. If successful, returns a JWT token" +
                    " for accessing protected resources."
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO) throws ErrorWhileAuth {
        AuthenticationResponseDTO response = authenticationService.login(userLoginRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "User Registration",
            description = "Registers a new user with the provided details. If registration is successful," +
                    " a JWT token is returned for accessing protected resources."
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequestDTO userRegisterRequestDTO)
            throws UserAlreadyExistsException, ErrorWhileAuth, UsernameAlreadyExistsException, EmailAlreadyExistsException {
        AuthenticationResponseDTO response = authenticationService.register(userRegisterRequestDTO);
        return ResponseEntity.ok(response);
    }
}