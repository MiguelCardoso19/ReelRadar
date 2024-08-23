package com.project.reelRadar.dto;

import com.project.reelRadar.exception.error.ErrorMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserUpdateRequestDTO(String username,
                                   @Email String email,
                                   String password) {}