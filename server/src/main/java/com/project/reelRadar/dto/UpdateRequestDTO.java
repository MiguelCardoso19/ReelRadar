package com.project.reelRadar.dto;

import com.project.reelRadar.exception.error.ErrorMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UpdateRequestDTO (@NotNull(message = ErrorMessage.USERNAME_EMPTY) String username,
                                @NotNull(message = ErrorMessage.EMAIL_EMPTY) @Email String email,
                                @NotNull(message = ErrorMessage.PASSWORD_EMPTY) String password) {}