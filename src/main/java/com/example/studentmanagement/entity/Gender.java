/**
 * Gender Enumeration
 *
 * <p>Defines the standardized gender options for students within the student management system.
 * This enumeration ensures consistent gender data representation across the application
 * and provides a constrained set of values for data integrity.</p>
 *
 * @version 1.0
 * @since 2025
 *
 * @author Student Management System Team
 */
package com.example.studentmanagement.entity;

/**
 * Enumeration representing gender options for student records.
 * Provides a simple, maintainable set of gender values that can be used
 * throughout the application for student demographic information.
 */
public enum Gender {

    /**
     * Male gender identifier.
     * Used for students who identify as male.
     */
    MALE,

    /**
     * Female gender identifier.
     * Used for students who identify as female.
     */
    FEMALE,

    /**
     * Inclusive gender identifier for identities beyond male/female.
     * Accommodates non-binary, genderqueer, and other gender expressions
     * to ensure comprehensive student representation.
     */
    OTHER
}