package com.nvkha.website.controller;


import com.nvkha.website.model.Message;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@javax.websocket.server.ServerEndpoint(value = "/chat/{username}")
public class ServerSocket {
    private static Set<Session> users = new HashSet<>();

    @OnOpen
    public void handleOpen(Session session, @PathParam("username") String username) throws IOException {
        users.add(session);
        session.getUserProperties().put("name", username);
        String msg = " has joined the chat";
        broadcast(username + ": ", msg);
    }

    @OnMessage
    public void handleMessage(String msg, Session session) throws IOException {
        String name = (String) session.getUserProperties().get("name");
        Message.save(new Message(name, msg, Timestamp.from(Instant.now())));
        broadcast(name + ":", msg);
    }

    @OnClose
    public void handleClose(Session session) throws IOException {
        String username = (String) session.getUserProperties().get("name");
        users.remove(session);
        String msg = " has left the chat";
        broadcast(username + ":", msg);
        System.out.println("Client disconnected");
    }

    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }

    /**
     * Broadcast message to all client
     * @param msg
     * @throws IOException
     */
    private static void broadcast(String name, String msg) throws IOException {
        for(Session user : users) {
            user.getBasicRemote().sendText(name + msg);
        }
    }
}
