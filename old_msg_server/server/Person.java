package com.nvkha.server;

import java.net.Socket;
import java.util.Objects;

public class Person {
    private String name;
    private String address;
    private Socket connection;

    public Person(String address, Socket client) {
        this.address = address;
        this.connection = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Socket getConnection() {
        return connection;
    }
}
