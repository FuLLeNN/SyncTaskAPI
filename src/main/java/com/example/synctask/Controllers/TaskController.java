package com.example.synctask.Controllers;

import com.example.synctask.DTOs.CreateTask;
import com.example.synctask.Models.Task;
import com.example.synctask.Services.TaskServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Tag(description = "",name = "Task Resource")
@RestController
@RequestMapping("api/tasks")
public class TaskController {
    private final TaskServiceImpl taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @Operation(summary  = "Get an task by id", description = "Returns a task by the id given")
    @GetMapping("/{id}")
    public Optional<Task> getTaskById(@PathVariable("id") Long id){
        return taskService.findTaskById(id);
    }

    @Operation(summary  = "Get all tasks", description = "Returns all tasks")
    @GetMapping("/")
    public List<Task> getAllTasks(){
        return taskService.findAll();
    }

    @Operation(summary  = "Create task", description = "Create task")
    @PostMapping("/")
    public Task createTask(@RequestBody CreateTask task){
        Task t = new Task();
        t.setName(task.getName());
        t.setDescription(task.getDescription());
        t.setStartDate(task.getStartDate());
        t.setEndDate(task.getEndDate());
        t.setUserId(task.getUserId());
        return taskService.saveTask(t);
    }

    @Operation(summary  = "Get all tasks by name", description = "Returns all tasks by the name $$$$")
    @GetMapping("/{name}")
    public List<Task> getTasksByName(@PathVariable("name") String name){
        return taskService.findTasksByName(name);
    }

    @Operation(summary  = "Get all tasks by description", description = "Returns all tasks by the description $$$$")
    @GetMapping("/{description}")
    public List<Task> getTasksByDescription(@PathVariable("description") String description){
        return taskService.findTasksByDescription(description);
    }

    @Operation(summary  = "Get all tasks by StartDate", description = "Returns all tasks by the StartDate $$$$")
    @GetMapping("/{startDate}")
    public List<Task> getTasksByStartDate(@PathVariable("startDate") LocalDate date){
        return taskService.findTasksByStartDate(date);
    }

    @Operation(summary  = "Get all tasks by EndDate", description = "Returns all tasks by the EndDate $$$$")
    @GetMapping("/{endDate}")
    public List<Task> getTasksByEndDate(@PathVariable("endDate") LocalDate date){
        return taskService.findTasksByEndDate(date);
    }

    @Operation(summary = "Update task by id", description = "Make changes on a task by the id")
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable("id") Long id, @RequestBody CreateTask task ){
        Task t = taskService.findTaskById(id).get();
        t.setName(task.getName());
        t.setDescription(task.getDescription());
        t.setStartDate(task.getStartDate());
        t.setEndDate(task.getEndDate());
        return taskService.updateTask(t);
    }


    @Operation(summary = "Delete task by id", description = "Delete a task by its id")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Long id){
        taskService.deleteTaskById(id);
    }
}
