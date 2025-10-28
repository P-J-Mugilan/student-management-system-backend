package com.example.studentmanagement.dto;

import com.example.studentmanagement.entity.Gender;
import jakarta.validation.constraints.*;

public class StudentRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Age is required")
    @Min(value = 15, message = "Age must be at least 15")
    @Max(value = 60, message = "Age must be less than or equal to 60")
    private Integer age;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Branch ID is required")
    private Long branchId;

    // Constructors
    public StudentRequest() {}

    public StudentRequest(String name, String email, Integer age, Gender gender, Long branchId) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.branchId = branchId;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getBranchId() {
        return branchId;
    }

    // Add this setter for the professor fix
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
}