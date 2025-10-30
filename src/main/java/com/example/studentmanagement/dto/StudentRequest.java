/**
 * Student Request DTO Class
 *
 * <p>Data Transfer Object for creating and updating student records.
 * Contains comprehensive validation rules for student information including
 * personal details, contact information, and academic branch assignment.
 * Ensures data integrity and proper student record management throughout
 * the system.</p>
 *
 * @version 1.0
 * @since 2025
 *
 * @author Student Management System Team
 */
package com.example.studentmanagement.dto;

import com.example.studentmanagement.entity.Gender;
import jakarta.validation.constraints.*;

/**
 * DTO for student creation and update operations.
 * Includes extensive validation constraints to maintain data quality
 * and ensure students meet the academic institution's requirements.
 * Supports both administrative and professor-led student management.
 */
public class StudentRequest {

    /**
     * Full name of the student.
     * Must meet length requirements for proper identification and record keeping.
     */
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    /**
     * Valid email address for the student.
     * Used for communication and must follow standard email format.
     * Should be unique across all student records.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    /**
     * Age of the student in years.
     * Constrained to reasonable academic age ranges (15-60 years).
     * Ensures students are within typical educational age boundaries.
     */
    @NotNull(message = "Age is required")
    @Min(value = 15, message = "Age must be at least 15")
    @Max(value = 60, message = "Age must be less than or equal to 30")
    private Integer age;

    /**
     * Gender identity of the student.
     * Required field using the standardized Gender enumeration.
     * Supports inclusive gender representation.
     */
    @NotNull(message = "Gender is required")
    private Gender gender;

    /**
     * Identifier of the academic branch the student belongs to.
     * Required field that links the student to their academic department.
     * Must reference an existing branch in the system.
     */
    @NotNull(message = "Branch ID is required")
    private Long branchId;

    // ==================== CONSTRUCTORS ====================

    /**
     * Default constructor required for JSON deserialization.
     * Initializes an empty StudentRequest instance.
     */
    public StudentRequest() {}

    /**
     * Parameterized constructor for creating a complete student request.
     *
     * @param name The full name of the student (2-100 characters)
     * @param email The valid email address of the student
     * @param age The age of the student (15-60 years)
     * @param gender The gender identity of the student
     * @param branchId The identifier of the student's academic branch
     */
    public StudentRequest(String name, String email, Integer age, Gender gender, Long branchId) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.branchId = branchId;
    }

    // ==================== GETTER AND SETTER METHODS ====================

    /**
     * Gets the full name of the student.
     *
     * @return The student's name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the full name of the student.
     * Subject to validation: cannot be blank and must be 2-100 characters.
     *
     * @param name The student name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email address of the student.
     *
     * @return The student's email as a String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the student.
     * Subject to validation: cannot be blank and must be a valid email format.
     *
     * @param email The student email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the age of the student.
     *
     * @return The student's age as an Integer
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Sets the age of the student.
     * Subject to validation: cannot be null and must be between 15-60 years.
     *
     * @param age The student age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Gets the gender of the student.
     *
     * @return The student's gender as a Gender enum value
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the gender of the student.
     * Required field that must use the standardized Gender enumeration.
     *
     * @param gender The student gender to set
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Gets the branch identifier for the student.
     *
     * @return The branch ID as a Long
     */
    public Long getBranchId() {
        return branchId;
    }

    /**
     * Sets the branch identifier for the student.
     * Required field that must reference an existing academic branch.
     * Used for both administrative and professor-led student management.
     *
     * @param branchId The branch ID to set
     */
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    /**
     * Returns a string representation of the student request.
     * Includes all fields except sensitive information for debugging purposes.
     *
     * @return String containing student name, email, age, gender, and branch ID
     */
    @Override
    public String toString() {
        return "StudentRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", branchId=" + branchId +
                '}';
    }
}