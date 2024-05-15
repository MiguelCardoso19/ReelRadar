package com.project.reelRadar.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

//I might need to change this dtos later since Records are not mutable
public record CustomerRecordDTO(@NotBlank String firstName, @NotBlank String lastName, @NotBlank @Email String email, @NotBlank String password) {
}
