/**
 * JWT Response DTO Class
 *
 * <p>Authentication response wrapper containing JWT token and user information.
 * Returned after successful login operations to provide the client with
 * authentication credentials and user context for subsequent API requests.
 * Follows standard JWT response format with Bearer token type.</p>
 *
 * @version 1.0
 * @since 2025
 *
 * @author Student Management System Team
 */
package com.example.studentmanagement.dto;

/**
 * DTO for JWT authentication responses.
 * Contains the access token, token type, and user identification information
 * required for authenticated API communication and client-side user management.
 */
public class JwtResponse {

    /**
     * JSON Web Token (JWT) for authenticated API access.
     * Contains encoded user claims and is used for authorization in subsequent requests.
     */
    private String token;

    /**
     * Token type specification, defaults to "Bearer".
     * Indicates the authentication scheme for the provided token.
     */
    private String type = "Bearer";

    /**
     * Username of the authenticated user.
     * Provides user identification for client-side display and context.
     */
    private String username;

    /**
     * Role of the authenticated user (e.g., "ADMIN", "PROFESSOR").
     * Determines access permissions and available functionality in the client application.
     */
    private String role;

    /**
     * Default constructor required for JSON deserialization.
     * Initializes a JwtResponse with default Bearer token type.
     */
    public JwtResponse() {}

    /**
     * Parameterized constructor for creating a complete JWT response.
     *
     * @param token The JWT access token for authentication
     * @param username The username of the authenticated user
     * @param role The role of the authenticated user
     */
    public JwtResponse(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    // ==================== GETTER AND SETTER METHODS ====================

    /**
     * Gets the JWT access token.
     *
     * @return The JWT token as a String
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the JWT access token.
     *
     * @param token The JWT token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets the token type (defaults to "Bearer").
     *
     * @return The token type as a String
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the token type.
     * Typically should remain as "Bearer" for JWT authentication.
     *
     * @param type The token type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the authenticated user's username.
     *
     * @return The username as a String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the authenticated user's username.
     *
     * @param username The username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the authenticated user's role.
     *
     * @return The user role as a String
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the authenticated user's role.
     *
     * @param role The user role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns a string representation of the JWT response.
     * Excludes the actual token value for security reasons.
     *
     * @return String containing token type, username, and role
     */
    @Override
    public String toString() {
        return "JwtResponse{" +
                "token='[PROTECTED]'" +
                ", type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}