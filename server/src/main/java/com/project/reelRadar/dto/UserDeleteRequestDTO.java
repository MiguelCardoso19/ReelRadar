package com.project.reelRadar.dto;

import jakarta.validation.constraints.NotNull;

import static com.project.reelRadar.exception.error.ErrorMessage.USERNAME_EMPTY;

public record UserDeleteRequestDTO(@NotNull(message = USERNAME_EMPTY) String username){}