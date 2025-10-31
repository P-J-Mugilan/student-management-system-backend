package com.example.studentmanagement.dto;

/**
 * JWT Response DTO Class
 *
 * <p>Authentication response wrapper containing JWT token, user information,
 * and branch information if the user is a professor.</p>
 *
 * @version 1.0
 * @since 2025
 */
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String username;
    private String role;
    private Long branchId;       // Null for admin
    private String branchName;   // Null for admin

    public JwtResponse() {}

    /**
     * Constructor for creating JWT response
     *
     * @param token JWT token string
     * @param username Username of authenticated user
     * @param role Role of user (ADMIN / PROFESSOR)
     * @param branchId Branch ID (null for admin)
     * @param branchName Branch name (null for admin)
     */
    public JwtResponse(String token, String username, String role, Long branchId, String branchName) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.branchId = branchId;
        this.branchName = branchName;
    }

    public JwtResponse(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;

    }

    // ==================== GETTERS & SETTERS ====================

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "token='[PROTECTED]'" +
                ", type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", branchId=" + branchId +
                ", branchName='" + branchName + '\'' +
                '}';
    }
}
