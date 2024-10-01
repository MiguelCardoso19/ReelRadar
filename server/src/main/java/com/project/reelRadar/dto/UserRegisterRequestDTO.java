package com.project.reelRadar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import static com.project.reelRadar.exception.error.ErrorMessage.*;

public record UserRegisterRequestDTO(@NotNull(message = USERNAME_EMPTY) String username,
                                     @NotNull(message = EMAIL_EMPTY) @Email String email,
                                     @NotNull(message = PASSWORD_EMPTY) String password) {}