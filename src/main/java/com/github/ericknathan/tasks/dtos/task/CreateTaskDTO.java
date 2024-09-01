package com.github.ericknathan.tasks.dtos.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateTaskDTO(
        @NotBlank @Size(min = 1, max = 50)
        @Schema(description = "Título da tarefa.", example = "Estudar Java")
        String title,

        @NotBlank @Size(min = 1, max = 500)
        @Schema(description = "Descrição da tarefa.", example = "Estudar Java utilizando o Nano Course da FIAP.")
        String description,

        @NotNull @FutureOrPresent
        @Schema(description = "Data de conclusão da tarefa.", example = "2024-10-31")
        LocalDate conclusionDate
) { }
