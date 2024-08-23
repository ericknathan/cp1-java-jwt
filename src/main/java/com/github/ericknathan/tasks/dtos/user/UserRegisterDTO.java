package com.github.ericknathan.tasks.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterDTO(
        @NotBlank @Size(min = 5, max = 100)
        String name,

        @NotBlank @Size(min = 5, max = 100)
        String email,

        @NotBlank @Size(min = 8)
        String password
) { }
