package com.nvkha.client;

import java.util.List;

public class UpdateMessage extends Thread {
    private Client client;
    private List<String> messages;
    public UpdateMessage(Client client, List<String> messages) {
        this.client = client;
        this.messages = messages;
    }

    @Override
    public void run() {
        boolean isRun = true;
        while (isRun){
            try {
                Thread.sleep(100); // Update new message receive from server every 0.1s
                List<String> messages = client.getMessages();
                for(String msg : messages) {
                    if(msg.equals("quit")) {
                        isRun = false;
                    }
                    this.messages.add(msg);
                    System.out.println(msg);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

