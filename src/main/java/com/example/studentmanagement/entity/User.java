/**
 * User Entity Class
 *
 * <p>Represents a system user with authentication credentials and role-based permissions.
 * This entity handles both administrative users and professors, with professors
 * being associated with specific academic branches for student management.</p>
 *
 * @version 1.0
 * @since 2025
 *
 * @author Student Management System Team
 */
package com.example.studentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * JPA Entity representing the users table in the database.
 * Stores user authentication information, role assignments, and branch associations.
 * Supports role-based access control with different permission levels.
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * Primary key identifier for the user.
     * Auto-generated unique identifier using database identity column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * Unique username for authentication and system identification.
     * This field is required and must be unique across all users.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Hashed password for user authentication.
     * Marked with JsonIgnore to prevent password exposure in API responses.
     * This field is required for all user accounts.
     */
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    /**
     * Role defining the user's permissions and access level.
     * Uses the Role enumeration to ensure consistent role assignments.
     * Required field that determines system capabilities.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /**
     * Academic branch associated with the user (for professors only).
     * Many-to-one relationship with Branch entity. This field is nullable
     * as administrators are not tied to specific branches. JsonIgnoreProperties
     * prevents circular references during JSON serialization.
     */
    @ManyToOne
    @JoinColumn(name = "branch_id")
    @JsonIgnoreProperties({"professors", "students"})
    private Branch branch; // Nullable for ADMIN

    /**
     * Default constructor required by JPA specification.
     * Initializes a new instance of the User class.
     */
    public User() {}

    /**
     * Parameterized constructor for creating a new user with complete information.
     *
     * @param username The unique username for authentication
     * @param password The hashed password for security
     * @param role The role defining user permissions
     * @param branch The academic branch (required for professors, null for admins)
     */
    public User(String username, String password, Role role, Branch branch) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.branch = branch;
    }

    // ==================== GETTER AND SETTER METHODS ====================

    /**
     * Gets the unique identifier of the user.
     *
     * @return The user ID as a Long value
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier of the user.
     * Typically used by JPA and should not be set manually in application code.
     *
     * @param userId The user ID to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

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
     * Must be unique across all users in the system.
     *
     * @param username The username to set
     * @throws IllegalArgumentException if username is null, empty, or not unique
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the hashed password for authentication.
     * Note: This method returns the actual password hash - use with caution.
     *
     * @return The hashed password as a String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the hashed password for authentication.
     * Passwords should always be hashed before being stored.
     *
     * @param password The hashed password to set
     * @throws IllegalArgumentException if password is null or empty
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the role defining user permissions.
     *
     * @return The user's role as a Role enum value
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role defining user permissions.
     *
     * @param role The user role to set
     * @throws IllegalArgumentException if role is null
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the academic branch associated with the user.
     * Returns null for administrative users who are not branch-specific.
     *
     * @return The Branch entity associated with this user, or null for admins
     */
    public Branch getBranch() {
        return branch;
    }

    /**
     * Sets the academic branch for the user.
     * Required for professors, should be null for administrators.
     *
     * @param branch The Branch entity to associate with this user
     */
    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    /**
     * Checks if the user is an administrator.
     * Convenience method for role-based logic.
     *
     * @return true if the user has ADMIN role, false otherwise
     */
    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    /**
     * Checks if the user is a professor.
     * Convenience method for role-based logic.
     *
     * @return true if the user has PROFESSOR role, false otherwise
     */
    public boolean isProfessor() {
        return this.role == Role.PROFESSOR;
    }

    /**
     * Returns a string representation of the user.
     * Excludes password for security reasons.
     *
     * @return String containing user ID, username, and role
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", branch=" + (branch != null ? branch.getName() : "null") +
                '}';
    }
}