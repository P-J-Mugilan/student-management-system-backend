/**
 * Login Request DTO Class
 *
 * <p>Authentication request wrapper for user login operations.
 * Contains validated credentials for user authentication with comprehensive
 * validation rules to ensure secure and proper credential formatting before
 * processing in the authentication system.</p>
 *
 * @version 1.0
 * @since 2025
 *
 * @author Student Management System Team
 */
package com.example.studentmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for user login requests.
 * Includes validation constraints to ensure credential security and
 * prevent common authentication vulnerabilities through proper input validation.
 */
public class LoginRequest {

    /**
     * Username for authentication.
     * Must be unique across the system and meet length requirements for security.
     */
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    /**
     * Password for authentication.
     * Enforces minimum length requirement to ensure password strength.
     * Should be hashed before storage and transmission over secure channels.
     */
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    /**
     * Default constructor required for JSON deserialization.
     * Initializes an empty LoginRequest instance.
     */
    public LoginRequest() {}

    /**
     * Parameterized constructor for creating a login request with credentials.
     *
     * @param username The username for authentication (3-50 characters)
     * @param password The password for authentication (minimum 6 characters)
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // ==================== GETTER AND SETTER METHODS ====================

    /**
     * Gets the username for authentication.
     *
     * @return The username as a String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for authentication.
     * Subject to validation: cannot be blank and must be 3-50 characters.
     *
     * @param username The username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password for authentication.
     *
     * @return The password as a String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for authentication.
     * Subject to validation: cannot be blank and must be at least 6 characters.
     *
     * @param password The password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the login request.
     * Excludes the actual password value for security reasons.
     *
     * @return String containing username (password is masked)
     */
    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}