package com.github.ericknathan.tasks.dtos.task;

import com.github.ericknathan.tasks.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateTaskDTO(
        @Size(min = 1, max = 50)
        @Schema(description = "Título da tarefa.", example = "Estudar Kotlin")
        String title,

        @Size(min = 1, max = 500)
        @Schema(description = "Descrição da tarefa.", example = "Estudar Kotlin utilizando o Nano Course da FIAP.")
        String description,

        @FutureOrPresent
        @Schema(description = "Data de conclusão da tarefa.", example = "2024-10-20")
        LocalDate conclusionDate,

        @Schema(description = "Status da tarefa.", example = "CONCLUIDA")
        TaskStatus status
) { }
