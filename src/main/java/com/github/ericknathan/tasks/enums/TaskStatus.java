package com.github.ericknathan.tasks.enums;

import lombok.Getter;

@Getter
public enum TaskStatus {
    PENDING("Pendente"),
    IN_PROGRESS("Em andamento"),
    DONE("Concluída");

    private final String name;

    TaskStatus(String name) {
        this.name = name;
    }
}
