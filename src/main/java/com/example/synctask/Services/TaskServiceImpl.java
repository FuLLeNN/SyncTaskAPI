package com.example.synctask.Services;

import com.example.synctask.Models.Task;
import com.example.synctask.Repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findTasksByName(String name) {
        return taskRepository.findByName(name);
    }

    @Override
    public List<Task> findTasksByDescription(String description) {
        return taskRepository.findByDescription(description);
    }

    @Override
    public List<Task> findTasksByStartDate(LocalDate date) {
        return taskRepository.findByStartDate(date);
    }

    @Override
    public List<Task> findTasksByEndDate(LocalDate date) {
        return taskRepository.findByEndDate(date);
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }


}
