/**
 * Conflict Exception
 *
 * Thrown for data conflicts (HTTP 409).
 * Used when request conflicts with current system state (duplicates, etc.).
 */
package com.example.studentmanagement.exception;

public class ConflictException extends BaseException {
    public ConflictException(String message) {
        super(message);
    }
}