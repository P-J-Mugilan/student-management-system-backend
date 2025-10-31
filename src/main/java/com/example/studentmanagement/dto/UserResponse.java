package com.example.studentmanagement.dto;

import com.example.studentmanagement.entity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * User Response DTO
 *
 * <p>Data Transfer Object for API responses containing user information.
 * Provides a secure representation by excluding sensitive fields such as passwords.
 * Supports both administrator and professor accounts, including optional
 * branch details for professors.</p>
 *
 * @version 1.0
 * @since 2025
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    /** Unique identifier for the user account */
    private Long userId;

    /** Username of the user account */
    private String username;

    /** Role of the user determining permissions and access level */
    private Role role;

    /** Branch ID for professor accounts; null for administrators */
    private Long branchId;

    /** Branch name for professor accounts; null for administrators */
    private String branchName;

    // ==================== CONSTRUCTORS ====================

    /** Default constructor required for JSON deserialization */
    public UserResponse() {}

    /**
     * Parameterized constructor for creating a complete user response
     *
     * @param userId Unique identifier of the user
     * @param username Username of the user account
     * @param role Role defining user permissions
     * @param branchId Branch ID for professors, null for admins
     * @param branchName Branch name for professors, null for admins
     */
    public UserResponse(Long userId, String username, Role role, Long branchId, String branchName) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.branchId = branchId;
        this.branchName = branchName;
    }

    // ==================== GETTERS AND SETTERS ====================

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    // ==================== CONVENIENCE METHODS ====================

    /** @return true if user has ADMIN role */
    public boolean isAdmin() { return this.role == Role.ADMIN; }

    /** @return true if user has PROFESSOR role */
    public boolean isProfessor() { return this.role == Role.PROFESSOR; }

    /** @return true if branch information is available */
    public boolean hasBranch() { return this.branchId != null; }

    @Override
    public String toString() {
        return "UserResponse{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", branchId=" + branchId +
                ", branchName='" + branchName + '\'' +
                '}';
    }
}
