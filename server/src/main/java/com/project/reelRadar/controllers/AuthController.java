package com.project.reelRadar.controllers;

import com.project.reelRadar.dtos.DeleteRequestDTO;
import com.project.reelRadar.exceptions.UserAlreadyExistsException;
import com.project.reelRadar.exceptions.UserNotFoundException;
import com.project.reelRadar.models.User;
import com.project.reelRadar.dtos.LoginRequestDTO;
import com.project.reelRadar.dtos.RegisterRequestDTO;
import com.project.reelRadar.dtos.ResponseDTO;
import com.project.reelRadar.security.TokenService;
import com.project.reelRadar.repositories.UserRepository;
import com.project.reelRadar.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO loginRequestDTO) {
        User user = this.userRepository.findByUsername(loginRequestDTO.username()).orElseThrow(() -> new RuntimeException("Email not found"));

        if (passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getUsername(),user.getId(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO registerRequestDTO) throws UserAlreadyExistsException {
        User newUser = userService.save(registerRequestDTO);
        if (newUser == null) {
            return ResponseEntity.badRequest().build();
        }

        String token = this.tokenService.generateToken(newUser);
        return ResponseEntity.ok(new ResponseDTO(newUser.getUsername(),newUser.getId(), token));
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody DeleteRequestDTO deleteRequestDTO) throws UserNotFoundException {
        userService.delete(deleteRequestDTO);
        return ResponseEntity.ok("User deleted successfully");
    }
}

