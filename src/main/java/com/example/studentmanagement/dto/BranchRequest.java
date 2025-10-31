package com.example.studentmanagement.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for creating or updating a Branch.
 * Contains minimal information required to create/update a branch.
 */
public class BranchRequest {

    @NotBlank(message = "Branch name is required")
    private String name;

    private String description;

    public BranchRequest() {}

    public BranchRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // ==================== GETTERS & SETTERS ====================

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
