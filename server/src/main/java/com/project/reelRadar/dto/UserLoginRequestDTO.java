package com.project.reelRadar.dto;

import jakarta.validation.constraints.NotNull;

import static com.project.reelRadar.exception.error.ErrorMessage.*;

public record UserLoginRequestDTO(@NotNull(message = USERNAME_EMPTY) String username,
                                  @NotNull(message = PASSWORD_EMPTY) String password){}