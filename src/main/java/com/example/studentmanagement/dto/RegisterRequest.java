/**
 * Register Request DTO Class
 *
 * <p>User registration request wrapper for creating new system accounts.
 * Contains validated user information with role-based branch assignment
 * for professors. Enforces comprehensive validation rules to ensure data
 * integrity and proper user account creation with appropriate permissions.</p>
 *
 * @version 1.0
 * @since 2025
 *
 * @author Student Management System Team
 */
package com.example.studentmanagement.dto;

import com.example.studentmanagement.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for user registration requests.
 * Supports creation of both administrative and professor accounts with
 * appropriate branch assignments. Includes validation for secure credential
 * creation and proper role-based data structuring.
 */
public class RegisterRequest {

    /**
     * Unique username for the new user account.
     * Must meet length requirements and be unique across the system.
     */
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    /**
     * Password for the new user account.
     * Enforces minimum length requirement for security.
     * Should be hashed before storage in the database.
     */
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    /**
     * Role defining the user's system permissions and access level.
     * Required field that determines the user's capabilities within the system.
     */
    @NotNull(message = "Role is required")
    private Role role;

    /**
     * Branch identifier for professor accounts.
     * Required for PROFESSOR role users, optional for ADMIN users.
     * Links the professor to a specific academic department.
     */
    private Long branchId;

    /**
     * Default constructor required for JSON deserialization.
     * Initializes an empty RegisterRequest instance.
     */
    public RegisterRequest() {}

    /**
     * Parameterized constructor for creating a complete registration request.
     *
     * @param username The username for the new account (3-50 characters)
     * @param password The password for the new account (minimum 6 characters)
     * @param role The role defining user permissions
     * @param branchId The branch identifier (required for professors, optional for admins)
     */
    public RegisterRequest(String username, String password, Role role, Long branchId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.branchId = branchId;
    }

    // ==================== GETTER AND SETTER METHODS ====================

    /**
     * Gets the username for the new account.
     *
     * @return The username as a String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the new account.
     * Subject to validation: cannot be blank and must be 3-50 characters.
     *
     * @param username The username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password for the new account.
     *
     * @return The password as a String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the new account.
     * Subject to validation: cannot be blank and must be at least 6 characters.
     *
     * @param password The password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the role for the new account.
     *
     * @return The user role as a Role enum value
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role for the new account.
     * Required field that determines system access permissions.
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
     * Required when creating PROFESSOR role users, optional for ADMIN users.
     *
     * @param branchId The branch ID to set
     */
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    /**
     * Checks if the request is for a professor account.
     * Convenience method for role-based validation logic.
     *
     * @return true if the role is PROFESSOR, false otherwise
     */
    public boolean isProfessor() {
        return this.role == Role.PROFESSOR;
    }

    /**
     * Checks if the request is for an admin account.
     * Convenience method for role-based validation logic.
     *
     * @return true if the role is ADMIN, false otherwise
     */
    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    /**
     * Returns a string representation of the registration request.
     * Excludes the actual password value for security reasons.
     *
     * @return String containing username, role, and branch ID (password is masked)
     */
    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                ", role=" + role +
                ", branchId=" + branchId +
                '}';
    }
}