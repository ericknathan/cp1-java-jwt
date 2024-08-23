package com.github.ericknathan.tasks.controllers;


import com.github.ericknathan.tasks.dtos.task.CreateTaskDTO;
import com.github.ericknathan.tasks.dtos.task.TaskDetailsDTO;
import com.github.ericknathan.tasks.dtos.task.UpdateTaskDTO;
import com.github.ericknathan.tasks.models.TaskModel;
import com.github.ericknathan.tasks.models.UserModel;
import com.github.ericknathan.tasks.repositories.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @PostMapping
    public ResponseEntity<TaskDetailsDTO> createTask(@RequestBody @Valid CreateTaskDTO taskDTO, UriComponentsBuilder uriBuilder) {
        var user = getAuthenticatedUser();
        if(user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        var task = new TaskModel(taskDTO, user);
        taskRepository.save(task);

        var uri = uriBuilder.path("tasks/{id}").buildAndExpand(task.getId()).toUri();

        return ResponseEntity.created(uri).body(new TaskDetailsDTO(task));
    }

    @GetMapping
    public ResponseEntity<List<TaskDetailsDTO>> getAllTasks(Pageable pageable){
        var user = getAuthenticatedUser();
        if(user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        var tasks = taskRepository.findByUser(user, pageable)
                .stream().map(TaskDetailsDTO::new).toList();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskDetailsDTO> getTask(@PathVariable Long id){
        var user = getAuthenticatedUser();
        if(user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        var task = taskRepository.findByUserAndId(user, id);
        if(task == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new TaskDetailsDTO(task));
    }

    @PutMapping("{id}")
    public ResponseEntity<TaskDetailsDTO> updateTask(@PathVariable Long id, @RequestBody @Valid UpdateTaskDTO taskDTO){
        var user = getAuthenticatedUser();
        if(user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        var task = taskRepository.findByUserAndId(user, id);
        if(task == null) return ResponseEntity.notFound().build();

        task.update(taskDTO);
        taskRepository.save(task);

        return ResponseEntity.ok(new TaskDetailsDTO(task));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        var user = getAuthenticatedUser();
        if(user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        var task = taskRepository.findByUserAndId(user, id);
        if(task == null) return ResponseEntity.notFound().build();

        taskRepository.delete(task);

        return ResponseEntity.noContent().build();
    }

    private UserModel getAuthenticatedUser() {
        return (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
