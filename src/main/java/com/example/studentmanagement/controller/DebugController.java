/**
 * Debug Controller
 *
 * Provides test endpoints for debugging authentication and authorization.
 * Used during development to verify security configuration.
 */
package com.example.studentmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    /**
     * Public test endpoint - accessible without authentication
     */
    @GetMapping("/public")
    public String publicTest() {
        return "PUBLIC: This endpoint is accessible without authentication";
    }

    /**
     * Secure test endpoint - requires authentication
     */
    @GetMapping("/secure")
    public String secureTest() {
        return "SECURE: This endpoint requires authentication";
    }
}