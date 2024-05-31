package com.project.reelRadar.controllers;

import com.project.reelRadar.exceptions.UserAlreadyExistsException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO loginRequestDTO) {
        User user = this.userRepository.findByUsername(loginRequestDTO.email()).orElseThrow(() -> new RuntimeException("Email not found"));

        if (passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO registerRequestDTO) throws UserAlreadyExistsException {
        User newUser = userService.save(registerRequestDTO.password(), registerRequestDTO.username(), registerRequestDTO.email());

        if (newUser == null) {
            return ResponseEntity.badRequest().build();
        }

        String token = this.tokenService.generateToken(newUser);
        return ResponseEntity.ok(new ResponseDTO(newUser.getUsername(), token));
    }
}



/*
        Optional<User> user = this.userRepository.findByUsername(registerRequestDTO.email());

        if(user.isEmpty()){
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(registerRequestDTO.password()));
            newUser.setUsername(registerRequestDTO.username());
            newUser.setEmail(registerRequestDTO.email());

            this.userRepository.save(newUser);
 */