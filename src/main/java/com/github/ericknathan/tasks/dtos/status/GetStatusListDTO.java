package com.github.ericknathan.tasks.dtos.status;

import com.github.ericknathan.tasks.enums.TaskStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record GetStatusListDTO(
        List<String> status
) {
    public GetStatusListDTO() {
        this(Arrays.stream(TaskStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList()));
    }
}
