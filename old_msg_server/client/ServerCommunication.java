package com.nvkha.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ServerCommunication extends Thread {
    /**
     * Thread waiting to receive messages from server
     */
    private Socket conn;
    private DataInputStream in;
    private List<String> messages;

    public ServerCommunication(Socket conn, List<String> messages) {
        this.conn = conn;
        this.messages = messages;
        try {
            in = new DataInputStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        boolean isConnected = true;
        while (isConnected) {
            try {
                String msg = in.readUTF();
                if(msg.equals("quit")) {
                    isConnected = false;
                    in.close();
                    conn.close();
                }
                else {
                    messages.add(msg);
                    //System.out.println(msg);
                }
            } catch (IOException e) { // Maybe client has left the chat
                break;
            }
        }
    }
}
