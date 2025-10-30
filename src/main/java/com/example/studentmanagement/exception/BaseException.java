/**
 * Base Exception Class
 *
 * Root exception for all custom exceptions in the system.
 * Provides consistent exception handling across the application.
 */
package com.example.studentmanagement.exception;

public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}