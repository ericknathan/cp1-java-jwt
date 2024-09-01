package com.github.ericknathan.tasks.repositories;

import com.github.ericknathan.tasks.models.TaskModel;
import com.github.ericknathan.tasks.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskModel, Long> {
    Page<TaskModel> findAllByUser(UserModel user, Pageable pageable);
    TaskModel findByUserAndId(UserModel user, Long id);
}
