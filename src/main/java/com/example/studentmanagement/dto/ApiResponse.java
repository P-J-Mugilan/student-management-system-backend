/**
 * API Response DTO Class
 *
 * <p>Standardized response wrapper for all API endpoints in the system.
 * Provides consistent response structure across all REST APIs with support
 * for success/error handling, HTTP status codes, and optional data payloads.
 * Uses Jackson annotations for proper JSON serialization.</p>
 *
 * @version 1.0
 * @since 2025
 *
 * @author Student Management System Team
 */
package com.example.studentmanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

/**
 * Generic response wrapper for API responses.
 * Supports typed data payloads and standardized error/success messaging.
 *
 * @param <T> The type of data payload included in the response
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    /**
     * Indicates whether the API request was successful.
     * True for successful operations, false for errors.
     */
    private boolean success;

    /**
     * Human-readable message describing the operation result.
     * Provides context for both success and error scenarios.
     */
    private String message;

    /**
     * Optional data payload returned by the API.
     * Contains the actual response data for successful operations.
     * Null for error responses or when no data needs to be returned.
     */
    private T data;

    /**
     * HTTP status text (e.g., "OK", "Created", "Bad Request").
     * Mirrors the HTTP status code in human-readable form.
     */
    private String status;

    /**
     * HTTP status code (e.g., 200, 201, 400, 404, 500).
     * Standard HTTP status code indicating the request outcome.
     */
    private Integer statusCode;

    /**
     * Default constructor required for JSON deserialization.
     * Initializes an empty API response instance.
     */
    public ApiResponse() {}

    /**
     * Full parameterized constructor for creating complete API responses.
     *
     * @param success Indicates if the operation was successful
     * @param message Descriptive message about the operation result
     * @param data Optional data payload (null for errors or no data)
     * @param httpStatus HTTP status information including code and reason phrase
     */
    public ApiResponse(boolean success, String message, T data, HttpStatus httpStatus) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.status = httpStatus.getReasonPhrase();
        this.statusCode = httpStatus.value();
    }

    // ==================== SUCCESS FACTORY METHODS ====================

    /**
     * Creates a successful API response with custom HTTP status.
     *
     * @param <T> The type of data payload
     * @param message Success message describing the operation
     * @param data Data payload to include in the response
     * @param httpStatus HTTP status code and reason phrase
     * @return ApiResponse instance configured for success
     */
    public static <T> ApiResponse<T> success(String message, T data, HttpStatus httpStatus) {
        return new ApiResponse<>(true, message, data, httpStatus);
    }

    /**
     * Creates a successful API response with HTTP 200 OK status.
     *
     * @param <T> The type of data payload
     * @param message Success message describing the operation
     * @param data Data payload to include in the response
     * @return ApiResponse instance with HTTP 200 OK status
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return success(message, data, HttpStatus.OK);
    }

    /**
     * Creates a successful API response with only a message (no data).
     *
     * @param <T> The type of data payload
     * @param message Success message describing the operation
     * @return ApiResponse instance with message only
     */
    public static <T> ApiResponse<T> success(String message) {
        return success(message, null, HttpStatus.OK);
    }

    /**
     * Creates a successful API response for resource creation (HTTP 201 Created).
     *
     * @param <T> The type of data payload
     * @param message Success message describing the creation
     * @param data Created resource data to include in the response
     * @return ApiResponse instance with HTTP 201 Created status
     */
    public static <T> ApiResponse<T> created(String message, T data) {
        return new ApiResponse<>(true, message, data, HttpStatus.CREATED);
    }

    // ==================== ERROR FACTORY METHODS ====================

    /**
     * Creates an error API response with custom HTTP status.
     *
     * @param <T> The type of data payload
     * @param message Error message describing what went wrong
     * @param httpStatus HTTP status code indicating the error type
     * @return ApiResponse instance configured for error
     */
    public static <T> ApiResponse<T> error(String message, HttpStatus httpStatus) {
        return new ApiResponse<>(false, message, null, httpStatus);
    }

    /**
     * Creates an error API response with HTTP 400 Bad Request status.
     *
     * @param <T> The type of data payload
     * @param message Error message describing what went wrong
     * @return ApiResponse instance with HTTP 400 Bad Request status
     */
    public static <T> ApiResponse<T> error(String message) {
        return error(message, HttpStatus.BAD_REQUEST);
    }

    // ==================== GETTER AND SETTER METHODS ====================

    /**
     * Checks if the API request was successful.
     *
     * @return true if successful, false if error occurred
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success status of the API response.
     *
     * @param success true for success, false for error
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Gets the descriptive message from the API response.
     *
     * @return The message describing the operation result
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the descriptive message for the API response.
     *
     * @param message The message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the data payload from the API response.
     *
     * @return The data payload, or null if no data
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the data payload for the API response.
     *
     * @param data The data payload to set
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Gets the HTTP status text.
     *
     * @return The HTTP status reason phrase
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the HTTP status text.
     *
     * @param status The HTTP status reason phrase
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the HTTP status code.
     *
     * @return The HTTP status code as integer
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the HTTP status code.
     *
     * @param statusCode The HTTP status code to set
     */
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Returns a string representation of the API response.
     * Excludes data payload for security and brevity.
     *
     * @return String containing success status, message, and HTTP status
     */
    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}