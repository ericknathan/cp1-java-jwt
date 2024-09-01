package com.github.ericknathan.tasks.dtos.task;

import com.github.ericknathan.tasks.enums.TaskStatus;
import com.github.ericknathan.tasks.models.TaskModel;

public record TaskDetailsDTO(
        Long id,
        String title,
        String description,
        String conclusionDate,
        String creationDate,
        String updateDate,
        TaskStatus status
) {
    public TaskDetailsDTO(TaskModel task) {
        this(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getConclusionDate().toString(),
                task.getCreationDate().toString(),
                task.getUpdateDate().toString(),
                task.getStatus()
        );
    }
}
