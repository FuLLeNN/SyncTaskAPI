package com.example.synctask.WebSockets;
import com.example.synctask.Models.Task;
import com.example.synctask.Services.TaskServiceImpl;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Component
@ServerEndpoint("/hub")
public class Hub {
    @Autowired
    private TaskServiceImpl taskService;
    @OnOpen
    public void onOpen(Session session, String groupId) {
        Long groupID = Long.parseLong(groupId);
        List<Task> groupTasks = taskService.getTasksByGroup(groupID);

        // Send the group tasks to the connected client
        session.getAsyncRemote().sendObject(groupTasks);
    }




}
