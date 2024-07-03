package com.project.reelRadar.dtos;

import com.project.reelRadar.errors.ErrorMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDTO (@NotNull(message = ErrorMessage.USERNAME_EMPTY) String username,
                                  @NotNull(message = ErrorMessage.EMAIL_EMPTY) @Email String email,
                                  @NotNull(message = ErrorMessage.PASSWORD_EMPTY) String password) {}