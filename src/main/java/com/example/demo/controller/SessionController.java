package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @GetMapping("/start-session")
    public String startSession( HttpSession session) {
        session.setAttribute("username", "test_user");
        session.setAttribute("role", "admin");
        return "Session started with attributes!";
    }

    @GetMapping("/get-session")
    public String getSession(HttpSession session) {
        if (session == null) {
            return "No active session";
        }
        return "Session is active with ID: " + session.getId();
    }
}
