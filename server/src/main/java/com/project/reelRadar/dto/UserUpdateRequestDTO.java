package com.project.reelRadar.dto;

import jakarta.validation.constraints.Email;

public record UserUpdateRequestDTO(String username,
                                   @Email String email,
                                   String password) {}