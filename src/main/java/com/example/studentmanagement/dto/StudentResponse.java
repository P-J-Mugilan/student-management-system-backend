package com.example.studentmanagement.dto;

import com.example.studentmanagement.entity.Gender;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Student Response DTO
 *
 * <p>Data Transfer Object for student information responses.
 * Provides a clean, structured representation of student data
 * for API responses, including optional branch details. Excludes
 * sensitive information and maps directly from Student entity.</p>
 *
 * @version 1.0
 * @since 2025
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentResponse {

    /** Unique identifier of the student */
    private Long studentId;

    /** Full name of the student */
    private String name;

    /** Email address of the student */
    private String email;

    /** Age of the student */
    private Integer age;

    /** Gender of the student */
    private Gender gender;

    /** Identifier of the academic branch */
    private Long branchId;

    /** Name of the academic branch */
    private String branchName;

    // ==================== CONSTRUCTORS ====================

    /** Default constructor required for JSON deserialization */
    public StudentResponse() {}

    /**
     * Parameterized constructor for a complete student response
     *
     * @param studentId Unique identifier of the student
     * @param name Full name of the student
     * @param email Email address of the student
     * @param age Age of the student
     * @param gender Gender of the student
     * @param branchId Identifier of the academic branch
     * @param branchName Name of the academic branch
     */
    public StudentResponse(Long studentId, String name, String email, Integer age, Gender gender, Long branchId, String branchName) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.branchId = branchId;
        this.branchName = branchName;
    }

    // ==================== GETTERS AND SETTERS ====================

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

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

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    // ==================== UTILITY METHODS ====================

    @Override
    public String toString() {
        return "StudentResponse{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", branchId=" + branchId +
                ", branchName='" + branchName + '\'' +
                '}';
    }
}
