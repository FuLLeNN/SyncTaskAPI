package com.example.synctask.core;
import com.example.synctask.Models.Groups;
import com.example.synctask.Services.GroupServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private GroupServiceImpl groupService;

    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String groupId = extractGroupIdFromUrl(session);
        if (groupId != null) {
            session.getAttributes().put("groupId", groupId);
        }
        sessions.add(session);
        sendTaskUpdateToSessions(Long.valueOf(groupId));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public void sendTaskUpdateToSessions(Long groupId) throws JsonProcessingException {
        Groups g = groupService.findById(groupId);
        String tasks = groupService.convertTasksListToJson(g.getTasks());
        for (WebSocketSession session : sessions) {
            Map<String, Object> attributes = session.getAttributes();
            Long sessionGroupId = Long.valueOf(String.valueOf(attributes.get("groupId")));
            if (groupId.equals(sessionGroupId)) {
                try {
                    session.sendMessage(new TextMessage(tasks));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String extractGroupIdFromUrl(WebSocketSession session) {
        String url = session.getUri().toString();
        String groupId = null;
        int index = url.lastIndexOf("/");
        if (index != -1 && index < url.length() - 1) {
            groupId = url.substring(index + 1);
        }
        return groupId;
    }

}