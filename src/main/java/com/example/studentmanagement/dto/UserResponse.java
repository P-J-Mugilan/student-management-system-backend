/**
 * User Response DTO Class
 *
 * <p>Data Transfer Object for user information responses, providing a clean
 * and secure representation of user data without exposing sensitive information
 * like passwords. Includes user details with optional branch information
 * for professor accounts, supporting both administrative and professor user types.</p>
 *
 * @version 1.0
 * @since 2025
 *
 * @author Student Management System Team
 */
package com.example.studentmanagement.dto;

import com.example.studentmanagement.entity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO for user information responses in API endpoints.
 * Provides a secure view of user data by excluding sensitive information
 * and including relevant branch details for professor accounts.
 * Uses JSON include non-null to optimize response payload size.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    /**
     * Unique identifier for the user account.
     * Primary key used for user identification and reference.
     */
    private Long userId;

    /**
     * Username for the user account.
     * Unique identifier used for authentication and display purposes.
     */
    private String username;

    /**
     * Role defining the user's system permissions and access level.
     * Determines the user's capabilities and available functionality.
     */
    private Role role;

    /**
     * Branch identifier for professor accounts.
     * Null for administrative users, contains branch ID for professors.
     * Links the professor to their assigned academic department.
     */
    private Long branchId;

    /**
     * Name of the branch associated with the professor.
     * Provides human-readable branch information for display purposes.
     * Null for administrative users who are not branch-specific.
     */
    private String branchName;

    // ==================== CONSTRUCTORS ====================

    /**
     * Default constructor required for JSON deserialization.
     * Initializes an empty UserResponse instance.
     */
    public UserResponse() {}

    /**
     * Parameterized constructor for creating a complete user response.
     *
     * @param userId The unique identifier of the user
     * @param username The username of the user account
     * @param role The role defining user permissions
     * @param branchId The branch identifier (for professors, null for admins)
     * @param branchName The branch name (for professors, null for admins)
     */
    public UserResponse(Long userId, String username, Role role, Long branchId, String branchName) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.branchId = branchId;
        this.branchName = branchName;
    }

    // ==================== GETTER AND SETTER METHODS ====================

    /**
     * Gets the unique identifier of the user.
     *
     * @return The user ID as a Long
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param userId The user ID to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the username of the user account.
     *
     * @return The username as a String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user account.
     *
     * @param username The username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the role of the user.
     *
     * @return The user role as a Role enum value
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role The user role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the branch identifier for professor accounts.
     *
     * @return The branch ID as a Long, or null for admin users
     */
    public Long getBranchId() {
        return branchId;
    }

    /**
     * Sets the branch identifier for professor accounts.
     *
     * @param branchId The branch ID to set (null for admin users)
     */
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    /**
     * Gets the branch name for professor accounts.
     *
     * @return The branch name as a String, or null for admin users
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * Sets the branch name for professor accounts.
     *
     * @param branchName The branch name to set (null for admin users)
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * Checks if the user is an administrator.
     * Convenience method for role-based logic in client applications.
     *
     * @return true if the user has ADMIN role, false otherwise
     */
    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    /**
     * Checks if the user is a professor.
     * Convenience method for role-based logic in client applications.
     *
     * @return true if the user has PROFESSOR role, false otherwise
     */
    public boolean isProfessor() {
        return this.role == Role.PROFESSOR;
    }

    /**
     * Checks if the user has branch information.
     * Useful for determining if branch details should be displayed.
     *
     * @return true if branchId is not null, false otherwise
     */
    public boolean hasBranch() {
        return this.branchId != null;
    }

    /**
     * Returns a string representation of the user response.
     * Includes all user information for debugging and logging purposes.
     *
     * @return String containing user ID, username, role, and branch information
     */
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