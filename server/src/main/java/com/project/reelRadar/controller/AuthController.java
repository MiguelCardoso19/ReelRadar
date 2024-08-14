package com.project.reelRadar.controller;

import com.project.reelRadar.dto.DeleteRequestDTO;
import com.project.reelRadar.exception.UserAlreadyExistsException;
import com.project.reelRadar.exception.UserNotFoundException;
import com.project.reelRadar.model.User;
import com.project.reelRadar.dto.LoginRequestDTO;
import com.project.reelRadar.dto.RegisterRequestDTO;
import com.project.reelRadar.dto.ResponseDTO;
import com.project.reelRadar.security.TokenService;
import com.project.reelRadar.repository.UserRepository;
import com.project.reelRadar.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        User user = this.userRepository.findByUsername(loginRequestDTO.username()).orElseThrow(() -> new RuntimeException("Email not found"));

        if (passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getUsername(),user.getId(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) throws UserAlreadyExistsException {
        User newUser = userService.save(registerRequestDTO);
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

