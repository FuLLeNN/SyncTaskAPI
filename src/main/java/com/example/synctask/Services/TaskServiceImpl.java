package com.example.synctask.Services;

import com.example.synctask.Models.Task;
import com.example.synctask.Repositories.GroupRepository;
import com.example.synctask.Repositories.TaskRepository;
import com.example.synctask.Repositories.UserRepository;
import com.example.synctask.core.WebSocketHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final WebSocketHandler webSocketHandler;

    public TaskServiceImpl(TaskRepository taskRepository, GroupRepository groupRepository, UserRepository userRepository, WebSocketHandler webSocketHandler) {
        this.taskRepository = taskRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.webSocketHandler = webSocketHandler;
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
    public List<Task> findTasksByStartDate(String date) {
        return taskRepository.findByStartDate(date);
    }

    @Override
    public List<Task> findTasksByEndDate(String date) {
        return taskRepository.findByEndDate(date);
    }

    @Override
    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteTaskById(Long id) {
        Task task = taskRepository.findById(id).get();
        userRepository.findById(task.getUserId()).get().getTasks().remove(task);
        if(task.isGroup())
            groupRepository.findByTasksId(id).getTasks().remove(task);
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> findAllByUserId(Long id) {
        return taskRepository.findAllByUserId(id);
    }

    public List<Task> getTasksByGroup(Long groupId) {
        // Retrieve tasks by group using the taskRepository
        List<Task> tasks = taskRepository.findByGroupId(groupId);
        return tasks;
    }


}
