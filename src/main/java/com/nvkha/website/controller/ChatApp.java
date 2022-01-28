package com.nvkha.website.controller;

import com.google.gson.Gson;
import com.nvkha.website.model.Message;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/home", "/login", "/logout", "/get-messages", "/get-name"})
public class ChatApp extends HttpServlet {
    private final String NAME_KEY = "name";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Message.createMessageTable();
        String path = req.getServletPath();
        HttpSession session = req.getSession();
        String name = (String) session.getAttribute(NAME_KEY);
        switch (path) {
            case "/home":
                if (name == null) {
                    resp.sendRedirect(req.getContextPath() + "/login");
                    return;
                }
                req.setAttribute("title", "Home");
                req.setAttribute("login", true);
                req.setAttribute("session", session);
                RequestDispatcher home = req.getRequestDispatcher("/templates/home.jsp");
                home.forward(req, resp);
                break;
            case "/login":
                req.setAttribute("title", "Login");
                req.setAttribute("login", false);
                RequestDispatcher login = req.getRequestDispatcher("/templates/login.jsp");
                login.forward(req, resp);
                break;
            case "/logout":
                session.removeAttribute(NAME_KEY);
                resp.sendRedirect(req.getContextPath() + "/login");
                break;
            case "/get-messages":
                List<Message> messages = Message.getAll();
                String msgJson = new Gson().toJson(messages);
                System.out.println(msgJson);
                resp.setContentType("application/json");
                PrintWriter output = resp.getWriter();
                output.print(msgJson);
                output.flush();
                break;
            case "/get-name":
                JSONObject json = new JSONObject();
                json.put("name", name);
                resp.setContentType("application/json");
                PrintWriter out = resp.getWriter();
                out.print(json);
                out.flush();
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession();
        switch (path) {
            case "/login":
                session.setAttribute(NAME_KEY, req.getParameter("inputName"));
                String name = (String) session.getAttribute(NAME_KEY);
                if(name.length() <= 0) {
                    resp.sendRedirect(req.getContextPath() + "/login");
                    return;
                }
                resp.sendRedirect(req.getContextPath() + "/home");
                break;
        }
    }
}
