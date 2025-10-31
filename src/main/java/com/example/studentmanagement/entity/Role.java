/**
 * Role Enumeration
 *
 * <p>Defines the types of users in the Student Management System and
 * establishes role-based access control (RBAC) for the application.</p>
 *
 * @version 1.0
 * @since 2025
 */
package com.example.studentmanagement.entity;

/**
 * Enumeration representing the roles assigned to system users.
 * Determines the access level and permissions available to a user.
 */
public enum Role {

    /**
     * Administrator role with full system access.
     * Can manage all branches, users, and students.
     * Has permissions to perform all CRUD operations system-wide.
     */
    ADMIN,

    /**
     * Professor role with branch-specific access.
     * Can manage students only within their assigned branch.
     * Permissions are limited to student management within their department.
     */
    PROFESSOR
}
