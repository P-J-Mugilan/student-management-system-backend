package com.example.studentmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Login Request DTO
 *
 * <p>Data Transfer Object for user login operations.
 * Encapsulates username and password with validation constraints
 * to ensure secure input handling before authentication processing.</p>
 *
 * @version 1.0
 * @since 2025
 */
public class LoginRequest {

    /** Username for authentication (3-50 characters) */
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    /** Password for authentication (minimum 6 characters) */
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    // ==================== CONSTRUCTORS ====================

    /** Default constructor required for JSON deserialization */
    public LoginRequest() {}

    /**
     * Parameterized constructor for login request
     *
     * @param username Username for authentication
     * @param password Password for authentication
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // ==================== GETTERS AND SETTERS ====================

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // ==================== UTILITY METHODS ====================

    /**
     * Returns a string representation of the login request.
     * Password is masked for security.
     *
     * @return String containing username and masked password
     */
    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}
