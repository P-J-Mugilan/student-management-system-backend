package com.example.studentmanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String status;
    private Integer statusCode;

    public ApiResponse() {}

    public ApiResponse(boolean success, String message, T data, HttpStatus httpStatus) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.status = httpStatus.getReasonPhrase();
        this.statusCode = httpStatus.value();
    }

    // Success methods
    public static <T> ApiResponse<T> success(String message, T data, HttpStatus httpStatus) {
        return new ApiResponse<>(true, message, data, httpStatus);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return success(message, data, HttpStatus.OK);
    }

    public static <T> ApiResponse<T> success(String message) {
        return success(message, null, HttpStatus.OK);
    }

    public static <T> ApiResponse<T> created(String message, T data) {
        return new ApiResponse<>(true, message, data, HttpStatus.CREATED);
    }

    // Error methods
    public static <T> ApiResponse<T> error(String message, HttpStatus httpStatus) {
        return new ApiResponse<>(false, message, null, httpStatus);
    }

    public static <T> ApiResponse<T> error(String message) {
        return error(message, HttpStatus.BAD_REQUEST);
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}