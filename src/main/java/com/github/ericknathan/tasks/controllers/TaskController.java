package com.github.ericknathan.tasks.controllers;


import com.github.ericknathan.tasks.dtos.task.CreateTaskDTO;
import com.github.ericknathan.tasks.dtos.task.TaskDetailsDTO;
import com.github.ericknathan.tasks.dtos.task.UpdateTaskDTO;
import com.github.ericknathan.tasks.models.TaskModel;
import com.github.ericknathan.tasks.models.UserModel;
import com.github.ericknathan.tasks.repositories.TaskRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tarefas", description = "Operações relacionadas à tarefas.")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @PostMapping
    @Operation(summary = "Criar uma tarefa", description = "Cria uma nova tarefa para o usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso.", content = @Content(schema = @Schema(implementation = TaskDetailsDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<TaskDetailsDTO> createTask(@RequestBody @Valid CreateTaskDTO taskDTO, UriComponentsBuilder uriBuilder) {
        var user = getAuthenticatedUser();

        var task = new TaskModel(taskDTO, user);
        taskRepository.save(task);

        var uri = uriBuilder.path("tasks/{id}").buildAndExpand(task.getId()).toUri();

        return ResponseEntity.created(uri).body(new TaskDetailsDTO(task));
    }

    @GetMapping
    @Operation(summary = "Listar tarefas", description = "Lista as tarefas do usuário autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefas listadas com sucesso.", content = @Content(schema = @Schema(implementation = TaskDetailsDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado.", content = @Content(schema = @Schema(hidden = true)))
    })
    @Parameters({
            @Parameter(name = "page", description = "Número da página", example = "0"),
            @Parameter(name = "size", description = "Tamanho da página", example = "10"),
            @Parameter(name = "sort", description = "Ordenação da página", example = "title,DESC")
    })
    public ResponseEntity<Page<TaskDetailsDTO>> getAllTasks(@Parameter(hidden = true) Pageable pageable){
        var user = getAuthenticatedUser();

        var tasks = taskRepository.findAllByUser(user, pageable).map(TaskDetailsDTO::new);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("{id}")
    @Parameters({ @Parameter(name = "id", description = "ID da tarefa", required = true, example = "1") })
    @Operation(summary = "Obter uma tarefa", description = "Obtém os detalhes de uma tarefa específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa obtida com sucesso.", content = @Content(schema = @Schema(implementation = TaskDetailsDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada.", content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<TaskDetailsDTO> getTask(@PathVariable Long id){
        var user = getAuthenticatedUser();

        var task = taskRepository.findByUserAndId(user, id);
        if(task == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new TaskDetailsDTO(task));
    }

    @PutMapping("{id}")
    @Parameters({ @Parameter(name = "id", description = "ID da tarefa", required = true, example = "1") })
    @Operation(summary = "Atualizar uma tarefa", description = "Atualiza os detalhes de uma tarefa específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso.", content = @Content(schema = @Schema(implementation = TaskDetailsDTO.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada.", content = @Content(schema = @Schema(hidden = true)))
    } )
    public ResponseEntity<TaskDetailsDTO> updateTask(@PathVariable Long id, @RequestBody @Valid UpdateTaskDTO taskDTO){
        var user = getAuthenticatedUser();

        var task = taskRepository.findByUserAndId(user, id);
        if(task == null) return ResponseEntity.notFound().build();

        task.update(taskDTO);
        taskRepository.save(task);

        return ResponseEntity.ok(new TaskDetailsDTO(task));
    }

    @DeleteMapping("{id}")
    @Parameters({ @Parameter(name = "id", description = "ID da tarefa", required = true, example = "1") })
    @Operation(summary = "Deletar uma tarefa", description = "Deleta uma tarefa específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada.", content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        var user = getAuthenticatedUser();

        var task = taskRepository.findByUserAndId(user, id);
        if(task == null) return ResponseEntity.notFound().build();

        taskRepository.delete(task);

        return ResponseEntity.noContent().build();
    }

    private UserModel getAuthenticatedUser() {
        return (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
