/**
 * Validation Error Response DTO Class
 *
 * <p>Specialized error response for field-level validation failures in API requests.
 * Provides detailed information about specific form field validation errors,
 * enabling client applications to display precise error messages adjacent to
 * the corresponding input fields for improved user experience.</p>
 *
 * @version 1.0
 * @since 2025
 *
 * @author Student Management System Team
 */
package com.example.studentmanagement.dto;

/**
 * DTO for field-specific validation error responses.
 * Used in validation error collections to provide clients with granular
 * error information for individual form fields during request validation.
 */
public class ValidationErrorResponse {

    /**
     * Name of the form field that failed validation.
     * Matches the field name in the request DTO (e.g., "username", "email", "branchId").
     * Allows clients to map errors to specific input fields in the user interface.
     */
    private String field;

    /**
     * Descriptive error message explaining the validation failure.
     * Provides specific guidance on what needs to be corrected for the field.
     * Should be user-friendly and actionable for end-users.
     */
    private String message;

    /**
     * Default constructor required for JSON deserialization.
     * Initializes an empty ValidationErrorResponse instance.
     */
    public ValidationErrorResponse() {}

    /**
     * Parameterized constructor for creating a complete validation error response.
     *
     * @param field The name of the field that failed validation
     * @param message The descriptive error message for the validation failure
     */
    public ValidationErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }

    // ==================== GETTER AND SETTER METHODS ====================

    /**
     * Gets the name of the field that failed validation.
     *
     * @return The field name as a String
     */
    public String getField() {
        return field;
    }

    /**
     * Sets the name of the field that failed validation.
     *
     * @param field The field name to set
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * Gets the descriptive error message for the validation failure.
     *
     * @return The error message as a String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the descriptive error message for the validation failure.
     *
     * @param message The error message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns a string representation of the validation error response.
     * Useful for debugging and logging validation failures.
     *
     * @return String containing field name and error message
     */

    @Override
    public String toString() {
        return "ValidationErrorResponse{" +
                "field='" + field + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}