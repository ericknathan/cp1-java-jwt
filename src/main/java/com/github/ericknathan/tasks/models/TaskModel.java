package com.github.ericknathan.tasks.models;


import com.github.ericknathan.tasks.dtos.task.CreateTaskDTO;
import com.github.ericknathan.tasks.dtos.task.UpdateTaskDTO;
import com.github.ericknathan.tasks.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="TB_TAREFA")
@Getter
@Setter
@NoArgsConstructor
public class TaskModel {
    @Id
    @GeneratedValue
    @Column(name="cd_tarefa")
    private Long id;

    @Column(name="ds_titulo", nullable = false, length = 100)
    private String title;

    @Column(name="ds_descricao", nullable = false, length = 255)
    private String description;

    @Column(name="dt_conclusao", nullable = false)
    private LocalDate conclusionDate;

    @CreationTimestamp
    @Column(name="dt_criacao", nullable = false, updatable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @UpdateTimestamp
    @Column(name="dt_atualizacao", nullable = false)
    private LocalDateTime updateDate = LocalDateTime.now();

    @Column(name="st_tarefa", nullable = false)
    private TaskStatus status = TaskStatus.PENDENTE;

    @ManyToOne
    @JoinColumn(name="cd_usuario")
    private UserModel user;

    public TaskModel(CreateTaskDTO taskDTO, UserModel user) {
        this.title = taskDTO.title();
        this.description = taskDTO.description();
        this.conclusionDate = taskDTO.conclusionDate();
        this.user = user;
    }

    public void update(UpdateTaskDTO taskDTO) {
        if(taskDTO.title() != null) this.title = taskDTO.title();
        if(taskDTO.description() != null) this.description = taskDTO.description();
        if(taskDTO.conclusionDate() != null) this.conclusionDate = taskDTO.conclusionDate();
        if(taskDTO.status() != null) this.status = taskDTO.status();
    }
}
