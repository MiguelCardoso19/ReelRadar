package com.project.reelRadar.dtos;

import com.project.reelRadar.errors.ErrorMessage;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO (@NotNull(message = ErrorMessage.USERNAME_EMPTY) String username,
                               @NotNull(message = ErrorMessage.PASSWORD_EMPTY) String password){}