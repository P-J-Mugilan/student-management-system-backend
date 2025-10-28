package com.example.studentmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @GetMapping("/public")
    public String publicTest() {
        return "PUBLIC: This endpoint is accessible without authentication";
    }

    @GetMapping("/secure")
    public String secureTest() {
        return "SECURE: This endpoint requires authentication";
    }
}