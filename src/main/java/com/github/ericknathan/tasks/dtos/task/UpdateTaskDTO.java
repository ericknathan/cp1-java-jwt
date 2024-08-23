package com.github.ericknathan.tasks.dtos.task;

import com.github.ericknathan.tasks.enums.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateTaskDTO(
        @Size(min = 1, max = 50)
        String title,

        @Size(min = 1, max = 500)
        String description,

        @FutureOrPresent
        LocalDate conclusionDate,

        TaskStatus status
) { }
