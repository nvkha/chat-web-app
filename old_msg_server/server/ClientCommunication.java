package com.nvkha.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientCommunication extends Thread {
    private Person person;
    private Socket conn;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientCommunication(Person person) {
        this.person = person;
        this.conn = this.person.getConnection();
        try {
            in = new DataInputStream(conn.getInputStream());
            out = new DataOutputStream(conn.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        // Set person name
        try {
            person.setName(in.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Wait for any massage from client
        try {
            Server.broadcast( "has joined chat", person.getName() + " ");
            boolean isConnected = true;
            while (isConnected) {
                String msg = in.readUTF();
                if(msg.equals("quit")) {
                    isConnected = false;
                    out.writeUTF("quit");
                    Server.disconnect(person);
                    System.out.println(person.getName() + " has left the chat");
                    Server.broadcast( "has left the chat", person.getName() + " ");
                }
                else {
                    System.out.println(person.getName() + ": " + msg);
                    Server.broadcast(msg, person.getName() + ": "); // Broadcast all massage of this client to all client
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
