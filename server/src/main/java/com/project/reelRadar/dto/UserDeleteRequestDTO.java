package com.project.reelRadar.dto;

import com.project.reelRadar.exception.error.ErrorMessage;
import jakarta.validation.constraints.NotNull;

public record UserDeleteRequestDTO(@NotNull(message = ErrorMessage.USERNAME_EMPTY) String username){}
