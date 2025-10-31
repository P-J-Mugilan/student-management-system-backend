/**
 * Gender Enumeration
 *
 * <p>Defines standardized gender options for students within the Student Management System.
 * This enumeration ensures consistent gender representation and data integrity across the application.</p>
 *
 * @version 1.0
 * @since 2025
 */
package com.example.studentmanagement.entity;

/**
 * Enumeration representing gender values for student records.
 * Provides a constrained, maintainable set of gender options.
 */
public enum Gender {

    /**
     * Male gender identifier.
     * For students who identify as male.
     */
    MALE,

    /**
     * Female gender identifier.
     * For students who identify as female.
     */
    FEMALE,

    /**
     * Inclusive gender identifier for identities beyond male/female.
     * Accommodates non-binary, genderqueer, and other gender expressions.
     */
    OTHER
}
