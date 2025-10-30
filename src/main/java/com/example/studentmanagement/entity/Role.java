/**
 * Role Enumeration
 *
 * <p>Defines the user role types within the student management system.
 * This enumeration establishes the access control levels and permissions
 * for different types of users in the application.</p>
 *
 * @version 1.0
 * @since 2025
 *
 * @author Student Management System Team
 */
package com.example.studentmanagement.entity;

/**
 * Enumeration representing user roles for authentication and authorization.
 * Determines the level of system access and available functionality for each user.
 */
public enum Role {

    /**
     * Administrator role with full system access.
     * Users with this role can manage all branches, users, and students.
     * Has permissions to perform all CRUD operations system-wide.
     */
    ADMIN,

    /**
     * Professor role with branch-specific access.
     * Users with this role can manage students within their assigned branch only.
     * Has limited permissions focused on student management within their department.
     */
    PROFESSOR
}