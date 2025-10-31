package com.example.studentmanagement.dto;

import com.example.studentmanagement.entity.Gender;
import jakarta.validation.constraints.*;

/**
 * Student Request DTO
 *
 * <p>Data Transfer Object used for creating and updating student records.
 * Contains validation rules for student details including personal information
 * and academic branch assignment. Ensures proper data integrity and consistency
 * in the system.</p>
 *
 * @version 1.0
 * @since 2025
 */
public class StudentRequest {

    /** Optional internal ID for update operations */
    private Long id;

    /** Full name of the student (2-100 characters) */
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    /** Unique, valid email address of the student */
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    /** Age of the student (15-30 years) */
    @NotNull(message = "Age is required")
    @Min(value = 15, message = "Age must be at least 15")
    @Max(value = 30, message = "Age must be less than or equal to 30")
    private Integer age;

    /** Gender of the student, using the standardized Gender enum */
    @NotNull(message = "Gender is required")
    private Gender gender;

    /** ID of the academic branch the student belongs to */
    @NotNull(message = "Branch ID is required")
    private Long branchId;

    // ==================== CONSTRUCTORS ====================

    /** Default constructor for JSON deserialization */
    public StudentRequest() {}

    /**
     * Parameterized constructor for creating a complete student request
     *
     * @param name Student's full name
     * @param email Student's email address
     * @param age Student's age
     * @param gender Student's gender
     * @param branchId ID of the academic branch
     */
    public StudentRequest(String name, String email, Integer age, Gender gender, Long branchId) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.branchId = branchId;
    }

    // ==================== GETTERS AND SETTERS ====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }

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
