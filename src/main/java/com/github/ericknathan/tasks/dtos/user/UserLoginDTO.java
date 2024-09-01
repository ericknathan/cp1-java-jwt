package com.github.ericknathan.tasks.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginDTO(
        @NotBlank @Size(min = 5, max = 100)
        @Schema(description = "E-mail do usuário.", example = "rafael.ronqui@fiap.com.br")
        String email,

        @NotBlank @Size(min = 8)
        @Schema(description = "Senha do usuário.", example = "12345678")
        String password
) { }
