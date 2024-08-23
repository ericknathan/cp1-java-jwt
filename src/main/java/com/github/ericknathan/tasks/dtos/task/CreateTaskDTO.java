package com.github.ericknathan.tasks.dtos.task;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateTaskDTO(
        @NotBlank @Size(min = 1, max = 50)
        String title,

        @NotBlank @Size(min = 1, max = 500)
        String description,

        @NotNull @FutureOrPresent
        LocalDate conclusionDate
) { }
