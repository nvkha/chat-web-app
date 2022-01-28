package com.nvkha.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final int PORT = 5050;

    private Socket conn;
    private static List<Person> persons;
    private ServerSocket server;

    public Server() {
        persons = new ArrayList<>();
        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Broadcast message to all client
     * @param msg
     * @param name
     */
    public static void broadcast(String msg, String name) {
        for(Person person : persons) {
            Socket conn = person.getConnection();
            try {
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.writeUTF(name + msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Disconnect to client
     * @param person
     */
    public static void disconnect(Person person) {
        persons.remove(person);
    }

    /**
     * Waiting for new connection from client
     */
    public void waitForConnection() {
        while (true) {
            try {
                System.out.println("[SERVER] Server is listening...");
                conn = server.accept();
                Person newPerson = new Person(conn.getInetAddress().toString(), conn);
                persons.add(newPerson);
                System.out.println("[SERVER] " + newPerson.getAddress() +
                        " connected to the server at " + LocalDateTime.now());
                ClientCommunication clientCommunication = new ClientCommunication(newPerson);
                clientCommunication.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.waitForConnection();
    }
}
