package com.nvkha.website.model;

import com.nvkha.website.utils.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Message {
    private int id;
    private String name;
    private String content;
    private Timestamp msg_date;

    public Message(int id, String name, String content, Timestamp msg_date) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.msg_date = msg_date;
    }

    public Message(String name, String content, Timestamp msg_date) {
        this.name = name;
        this.content = content;
        this.msg_date = msg_date;
    }

    public static void createMessageTable() {
        Connection conn = ConnectionUtils.getConnection();
        Statement sttm = null;
        String sql = "CREATE TABLE IF NOT EXISTS Message " +
                "(id SERIAL PRIMARY KEY, " +
                " name VARCHAR(255), " +
                " content TEXT, " +
                " msg_date TIMESTAMP)";
        try {
            sttm = conn.createStatement();
            sttm.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Message> getAll() {
        List<Message> result = new ArrayList<>();
        String sql = "SELECT * FROM Message ORDER BY id DESC LIMIT 100";
        Connection conn = ConnectionUtils.getConnection();
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            sttm = conn.prepareStatement(sql);
            rs = sttm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String content = rs.getString("content");
                Timestamp date = rs.getTimestamp("msg_date");
                result.add(new Message(id, name, content, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, sttm, rs);
        }
        return result;
    }

    public static void save(Message msg) {
        String sql = "INSERT INTO Message(name, content, msg_date) VALUES(?, ?, ?)";
        Connection conn = ConnectionUtils.getConnection();
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            sttm = conn.prepareStatement(sql);
            sttm.setString(1, msg.name);
            sttm.setString(2, msg.content);
            sttm.setTimestamp(3, msg.msg_date);
            sttm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, sttm, rs);
        }
    }

    private static void close(Connection conn, Statement sttm, ResultSet rs) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(sttm != null) {
            try {
                sttm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
