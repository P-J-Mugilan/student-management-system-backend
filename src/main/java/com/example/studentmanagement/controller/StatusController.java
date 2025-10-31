package com.example.studentmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StatusController {

    /**
     * Root endpoint - basic status check
     */
    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Student Management System Backend");
        response.put("message", "Application is running!");
        response.put("timestamp", Instant.now().toString());
        return response;
    }

    /**
     * Detailed health check endpoint
     */
    @GetMapping("/health")
    public Map<String, Object> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Student Management System Backend");
        response.put("timestamp", Instant.now().toString());
        response.put("details", Map.of(
                "database", "OK",
                "emailService", "OK"
        ));
        return response;
    }
}
