package com.nvkha.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Socket conn;
    private DataOutputStream out;
    private ServerCommunication serverCommunication;
    private List<String> messages;

    public Client(String name) {
        messages = new ArrayList<>();
        try {
            conn = new Socket("localhost", 5050);
            out = new DataOutputStream(conn.getOutputStream());
            receiveMassages();
            this.sendMassage(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create new thread to wait for new message from server
     */
    public void receiveMassages() {
        // Handler all massage from server in new thread
        serverCommunication = new ServerCommunication(conn, messages);
        serverCommunication.start();
    }

    /**
     * Send message to server
     * @param msg
     */
    public void sendMassage(String msg) {
        try {
            out.writeUTF(msg);
            messages.add(msg);
            if(msg.equals("quit")) {
                serverCommunication.join();
                out.close();
                conn.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnect to server
     */
    public void disconnect() {
        this.sendMassage("quit");
    }

    /**
     * Get all new message from client and clear all message
     * @return: all current message
     */
    public List<String> getMessages() {
        List<String> copyMessages = new ArrayList<>(messages);
        messages.clear();
        return copyMessages;
    }

    public static void main(String[] args) {
        Client newClient = new Client("Kha");
        newClient.sendMassage("Hello World!");
    }
}
