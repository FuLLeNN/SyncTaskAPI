package com.example.synctask.Services;

import com.example.synctask.Models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> findAll();
    Optional<Task> findTaskById(Long id);
    List<Task> findTasksByName(String name);
    List<Task> findTasksByDescription(String description);
    List<Task> findTasksByStartDate(String date);
    List<Task> findTasksByEndDate(String date);
    Task saveTask(Task task);
    Task updateTask(Task task);
    void deleteTaskById(Long id);
    List<Task> findAllByUserId(Long id);

    List<Task> getTasksByGroup(Long id);
}

