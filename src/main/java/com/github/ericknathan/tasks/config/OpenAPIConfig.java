package com.github.ericknathan.tasks.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Erick Nathan",
                        email = "erick.capito@hotmail.com",
                        url = "https://ericknathan.vercel.app"
                ),
                title = "API de Gerenciamento de Tarefas",
                description = "Especificação da API de Gerenciamento de Tarefas.",
                version = "1.0"
        ),
        servers = {
                @Server(description = "Ambiente de Desenvolvimento", url = "http://localhost:8080")
        },
        security = @SecurityRequirement(name = "Bearer Auth")
)
@SecurityScheme(
        name = "Bearer Auth",
        description = "Autenticação básica via Bearer Token JWT.",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig { }
