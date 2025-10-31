/**
 * User Entity Class
 *
 * <p>Represents a system user with authentication credentials and role-based permissions.
 * This entity handles both administrative users and professors, with professors
 * being associated with specific academic branches for student management.</p>
 *
 * @version 1.0
 * @since 2025
 */
package com.example.studentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

    /** Primary key identifier for the user. Auto-generated. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /** Unique username for authentication. Required. */
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Username is required")
    private String username;

    /** Hashed password for authentication. Hidden in JSON responses. */
    @JsonIgnore
    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    private String password;

    /** Role defining user's permissions. Uses Role enum. Required. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /**
     * Academic branch associated with the user (for professors only).
     * Admins do not have a branch.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    @JsonIgnoreProperties({"professors", "students", "hibernateLazyInitializer", "handler"})
    private Branch branch;

    /** Default constructor for JPA. */
    public User() {}

    /**
     * Constructor with full initialization.
     *
     * @param username Username for authentication
     * @param password Hashed password
     * @param role Role of the user
     * @param branch Associated branch (nullable for admins)
     */
    public User(String username, String password, Role role, Branch branch) {
        this.username = username != null ? username.trim() : null;
        this.password = password;
        this.role = role;
        this.branch = branch;
    }

    // ==================== GETTERS & SETTERS ====================

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username != null ? username.trim() : null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    // ==================== CONVENIENCE METHODS ====================

    /** Returns true if user is an administrator. */
    public boolean isAdmin() {
        return Role.ADMIN.equals(this.role);
    }

    /** Returns true if user is a professor. */
    public boolean isProfessor() {
        return Role.PROFESSOR.equals(this.role);
    }

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
