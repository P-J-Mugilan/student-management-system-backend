package com.example.studentmanagement.exception;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String resource, String field, Object value) {
        super(resource + " not found with " + field + ": " + value);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}