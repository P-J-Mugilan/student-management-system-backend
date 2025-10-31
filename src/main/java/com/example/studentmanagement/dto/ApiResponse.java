package com.example.studentmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

/**
 * API Response DTO
 *
 * <p>Standardized response wrapper for all API endpoints in the Student Management System.
 * Ensures consistent JSON structure for success and error responses with optional data payloads.
 * Automatically includes timestamp and HTTP status information.</p>
 *
 * @param <T> The type of the response payload (optional)
 *
 * @version 1.0
 * @since 2025
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    /** Indicates whether the API operation was successful. */
    private final boolean success;

    /** Human-readable message describing the response. */
    private final String message;

    /** Optional payload of the response. */
    private final T data;

    /** HTTP status reason phrase (e.g., "OK", "CREATED"). */
    private final String status;

    /** HTTP status code (e.g., 200, 201). */
    private final Integer statusCode;

    /** Timestamp of when the response was generated. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();

    /** Private constructor used internally by factory methods. */
    private ApiResponse(boolean success, String message, T data, HttpStatus httpStatus) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.status = httpStatus.getReasonPhrase();
        this.statusCode = httpStatus.value();
    }

    // ==================== FACTORY METHODS ====================

    /** Creates a success response with custom message, data, and HTTP status. */
    public static <T> ApiResponse<T> success(String message, T data, HttpStatus httpStatus) {
        return new ApiResponse<>(true, message, data, httpStatus);
    }

    /** Creates a success response with custom message and data. Defaults to 200 OK. */
    public static <T> ApiResponse<T> success(String message, T data) {
        return success(message, data, HttpStatus.OK);
    }

    /** Creates a success response with custom message only. Defaults to 200 OK. */
    public static <T> ApiResponse<T> success(String message) {
        return success(message, null, HttpStatus.OK);
    }

    /** Creates a success response indicating resource creation (201 CREATED). */
    public static <T> ApiResponse<T> created(String message, T data) {
        return new ApiResponse<>(true, message, data, HttpStatus.CREATED);
    }

    /** Creates a success response with no content (204 NO CONTENT). */
    public static <T> ApiResponse<T> noContent(String message) {
        return new ApiResponse<>(true, message, null, HttpStatus.NO_CONTENT);
    }

    /** Creates an error response with custom message and HTTP status. */
    public static <T> ApiResponse<T> error(String message, HttpStatus httpStatus) {
        return new ApiResponse<>(false, message, null, httpStatus);
    }

    /** Creates an error response with custom message. Defaults to 400 BAD REQUEST. */
    public static <T> ApiResponse<T> error(String message) {
        return error(message, HttpStatus.BAD_REQUEST);
    }

    // ==================== GETTERS ====================

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public String getStatus() { return status; }
    public Integer getStatusCode() { return statusCode; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format(
                "ApiResponse{success=%s, message='%s', status='%s', statusCode=%d, timestamp=%s}",
                success, message, status, statusCode, timestamp
        );
    }
}
