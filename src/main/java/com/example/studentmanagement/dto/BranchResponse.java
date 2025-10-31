package com.example.studentmanagement.dto;

import com.example.studentmanagement.entity.Branch;

/**
 * Response DTO for Branch entity.
 * Converts Branch entity to a clean API response.
 */
public class BranchResponse {

    private Long branchId;
    private String name;
    private String description;

    public BranchResponse(Branch branch) {
        this.branchId = branch.getBranchId();
        this.name = branch.getName();
        this.description = branch.getDescription();
    }

    public Long getBranchId() {
        return branchId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStudentCount(int i) {
    }

    public void setProfessorCount(int i) {
    }
}
