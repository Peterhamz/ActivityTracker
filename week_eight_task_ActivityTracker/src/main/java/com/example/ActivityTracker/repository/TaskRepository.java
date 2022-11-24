package com.example.ActivityTracker.repository;

import com.example.ActivityTracker.Enum.TaskStatus;
import com.example.ActivityTracker.model.Task;
import com.example.ActivityTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByIdAndUser(Long id, User user);

    List<Task> findByStatusAndUser(TaskStatus status, User user);



}
