/**
 * Update User Request DTO Class
 *
 * <p>Data Transfer Object for updating existing user accounts with partial updates.
 * Supports selective field updates where only provided fields are modified,
 * allowing for flexible user management without requiring complete user data
 * for every update operation. Uses JSON include non-null to handle partial updates.</p>
 *
 * @version 1.0
 * @since 2025
 *
 * @author Student Management System Team
 */
package com.example.studentmanagement.dto;

import com.example.studentmanagement.entity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for user update operations with partial update support.
 * Allows selective modification of user properties where only non-null values
 * are applied to the existing user record. Maintains role-based branch assignment
 * consistency for professor accounts.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserRequest {

    /**
     * Updated username for the user account.
     * Optional field - if provided, must be unique across the system.
     * If null, the existing username remains unchanged.
     */
    private String username;

    /**
     * Updated password for the user account.
     * Optional field - if provided, should be hashed before storage.
     * If null, the existing password remains unchanged.
     */
    private String password;

    /**
     * Updated role defining user permissions and access level.
     * Required field that must be provided in every update request.
     * Determines the user's system capabilities and branch requirements.
     */
    @NotNull(message = "Role is required")
    private Role role;

    /**
     * Updated branch identifier for professor accounts.
     * Optional field - required when role is PROFESSOR, must be null for ADMIN.
     * If null and role is PROFESSOR, maintains existing branch assignment.
     */
    private Long branchId;

    // ==================== GETTER AND SETTER METHODS ====================

    /**
     * Gets the updated username.
     *
     * @return The username as a String, or null if not being updated
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the updated username.
     * Optional field - if null, the existing username is preserved.
     *
     * @param username The username to set, or null to preserve existing
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the updated password.
     *
     * @return The password as a String, or null if not being updated
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the updated password.
     * Optional field - if null, the existing password is preserved.
     * Should be hashed before storage if provided.
     *
     * @param password The password to set, or null to preserve existing
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the updated role.
     *
     * @return The user role as a Role enum value (required field)
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the updated role.
     * Required field that must be provided in every update request.
     *
     * @param role The user role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the updated branch identifier.
     *
     * @return The branch ID as a Long, or null if not being updated
     */
    public Long getBranchId() {
        return branchId;
    }

    /**
     * Sets the updated branch identifier.
     * Required when role is PROFESSOR, must be null when role is ADMIN.
     * If null and role is PROFESSOR, maintains existing branch assignment.
     *
     * @param branchId The branch ID to set, or null for admin users
     */
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    /**
     * Checks if the update includes a username change.
     *
     * @return true if username is provided (non-null), false otherwise
     */
    public boolean hasUsernameUpdate() {
        return this.username != null;
    }

    /**
     * Checks if the update includes a password change.
     *
     * @return true if password is provided (non-null), false otherwise
     */
    public boolean hasPasswordUpdate() {
        return this.password != null;
    }

    /**
     * Checks if the update includes a branch assignment change.
     *
     * @return true if branchId is provided (non-null), false otherwise
     */
    public boolean hasBranchUpdate() {
        return this.branchId != null;
    }

    /**
     * Checks if the request is for updating to a professor role.
     *
     * @return true if the role is PROFESSOR, false otherwise
     */
    public boolean isProfessor() {
        return this.role == Role.PROFESSOR;
    }

    /**
     * Checks if the request is for updating to an admin role.
     *
     * @return true if the role is ADMIN, false otherwise
     */
    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    /**
     * Returns a string representation of the update user request.
     * Excludes the actual password value for security reasons.
     *
     * @return String containing username, role, and branch ID (password is masked)
     */
    @Override
    public String toString() {
        return "UpdateUserRequest{" +
                "username='" + username + '\'' +
                ", password='" + (password != null ? "[PROTECTED]" : "null") + '\'' +
                ", role=" + role +
                ", branchId=" + branchId +
                '}';
    }
}