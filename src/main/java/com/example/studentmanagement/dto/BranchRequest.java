/**
 * Branch Request DTO Class
 *
 * <p>Data Transfer Object for creating and updating branch information.
 * Contains validation rules for branch data input and serves as the
 * request body for branch-related API endpoints. Ensures data integrity
 * before processing in the service layer.</p>
 *
 * @version 1.0
 * @since 2025
 *
 * @author Student Management System Team
 */
package com.example.studentmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for branch creation and update operations.
 * Includes comprehensive validation constraints to ensure data quality
 * and prevent invalid branch information from entering the system.
 */
public class BranchRequest {

    /**
     * Name of the branch (e.g., "Computer Science", "Electrical Engineering").
     * Must be unique, non-empty, and within specified length limits.
     */
    @NotBlank(message = "Branch name is required")
    @Size(min = 2, max = 100, message = "Branch name must be between 2 and 100 characters")
    private String name;

    /**
     * Detailed description of the branch's purpose and focus areas.
     * Provides context about the branch's academic offerings and scope.
     */
    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

    /**
     * Default constructor required for JSON deserialization.
     * Initializes an empty BranchRequest instance.
     */
    public BranchRequest() {}

    /**
     * Parameterized constructor for creating a branch request with initial values.
     *
     * @param name The name of the branch (2-100 characters)
     * @param description The description of the branch (10-500 characters)
     */
    public BranchRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // ==================== GETTER AND SETTER METHODS ====================

    /**
     * Gets the branch name.
     *
     * @return The branch name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the branch name.
     * Subject to validation: cannot be blank and must be 2-100 characters.
     *
     * @param name The branch name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the branch description.
     *
     * @return The branch description as a String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the branch description.
     * Subject to validation: cannot be blank and must be 10-500 characters.
     *
     * @param description The branch description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of the branch request.
     * Includes both name and description for debugging purposes.
     *
     * @return String containing branch name and description
     */
    @Override
    public String toString() {
        return "BranchRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}