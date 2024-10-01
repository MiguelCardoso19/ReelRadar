package com.project.reelRadar.dto;

import jakarta.validation.constraints.NotNull;

import static com.project.reelRadar.exception.error.ErrorMessage.*;

public record FavoriteDeleteRequestDTO(@NotNull(message = TYPE_EMPTY)
                                       String type,@NotNull(message = VALUE_EMPTY) String value) {}