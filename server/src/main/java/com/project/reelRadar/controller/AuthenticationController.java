package com.project.reelRadar.controller;

import com.project.reelRadar.exception.ErrorWhileAuth;
import com.project.reelRadar.exception.UserAlreadyExistsException;
import com.project.reelRadar.model.User;
import com.project.reelRadar.dto.UserLoginRequestDTO;
import com.project.reelRadar.dto.UserRegisterRequestDTO;
import com.project.reelRadar.dto.ResponseDTO;
import com.project.reelRadar.security.TokenService;
import com.project.reelRadar.repository.UserRepository;
import com.project.reelRadar.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserService userService;

    @Operation(
            summary = "User Login",
            description = "Authenticates a user with their username and password. If successful, returns a JWT token for accessing protected resources."
    )
    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO) throws ErrorWhileAuth {
        User user = this.userRepository.findByUsername(userLoginRequestDTO.username()).orElseThrow(() -> new UsernameNotFoundException(userLoginRequestDTO.username()));

        if (passwordEncoder.matches(userLoginRequestDTO.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getUsername(),user.getId(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(
            summary = "User Registration",
            description = "Registers a new user with the provided details. If registration is successful, a JWT token is returned for accessing protected resources."
    )
    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody UserRegisterRequestDTO userRegisterRequestDTO) throws UserAlreadyExistsException, ErrorWhileAuth {
        User newUser = userService.save(userRegisterRequestDTO);
        if (newUser == null) {
            return ResponseEntity.badRequest().build();
        }

        String token = this.tokenService.generateToken(newUser);
        return ResponseEntity.ok(new ResponseDTO(newUser.getUsername(),newUser.getId(), token));
    }

    /**
     * This method is triggered when a method argument fails validation.
     * It constructs a response entity with the details of the validation errors.
     *
     * @param exception the exception that contains details about the validation errors.
     * @return a ResponseEntity containing a map of field names to their respective validation error messages,
     *         and an HTTP status code of BAD_REQUEST.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        var errors = new HashMap<String, String>();
                exception.getBindingResult().getFieldErrors()
                        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
                return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}