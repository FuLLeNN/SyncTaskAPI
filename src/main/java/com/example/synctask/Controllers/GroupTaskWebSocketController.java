package com.example.synctask.Controllers;


import com.example.synctask.Models.Task;
import com.example.synctask.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
public class GroupTaskWebSocketController {
    @Autowired
    private TaskService taskService;

    @MessageMapping("/tasks/{groupId}")
    @SendTo("/topic/tasks/{groupId}")
    public List<Task> getTasksByGroup(@DestinationVariable Long groupId) {
        // Retrieve tasks by group using the taskService
        List<Task> tasks = taskService.getTasksByGroup(groupId);
        return tasks;
    }
}
