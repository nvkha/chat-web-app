package com.nvkha.website.utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class ConnectionUtils {
    public static Connection getConnection() {
        String path = Paths.get(".").toAbsolutePath().normalize().toString();
        String url = "jdbc:postgresql://localhost/chat_db?user=postgres&password=1234";
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url);
            System.out.printf("Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        getConnection();
    }
}
