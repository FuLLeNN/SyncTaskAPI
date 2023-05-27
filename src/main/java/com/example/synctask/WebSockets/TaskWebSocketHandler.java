package com.example.synctask.WebSockets;

import com.example.synctask.Models.Task;
import com.example.synctask.Services.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.List;

@Component
public class TaskWebSocketHandler extends TextWebSocketHandler {

    private TaskService taskService;

    @Autowired
    public TaskWebSocketHandler(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // Parse the incoming message and extract the group ID
        String payload = message.getPayload();
        Long groupId = Long.parseLong(payload);


        // Retrieve all tasks for the specified group
        List<Task> tasks = taskService.getTasksByGroup(groupId);

        // Convert the tasks to JSON and send them back to the client
        ObjectMapper objectMapper = new ObjectMapper();
        String tasksJson = objectMapper.writeValueAsString(tasks);
        session.sendMessage(new TextMessage(tasksJson));
    }
}