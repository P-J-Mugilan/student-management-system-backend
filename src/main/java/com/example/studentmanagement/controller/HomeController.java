/**
 * Home Controller
 *
 * Provides basic health check and root endpoint.
 * Used to verify the application is running.
 */
package com.example.studentmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    /**
     * Root endpoint - health check
     */
    @GetMapping("/")
    public String home() {
        return "Student Management Backend is running!";
    }
}