package com.project.reelRadar.dto;

import com.project.reelRadar.exception.error.ErrorMessage;
import jakarta.validation.constraints.NotNull;

public record UserLoginRequestDTO(@NotNull(message = ErrorMessage.USERNAME_EMPTY) String username,
                                  @NotNull(message = ErrorMessage.PASSWORD_EMPTY) String password){}