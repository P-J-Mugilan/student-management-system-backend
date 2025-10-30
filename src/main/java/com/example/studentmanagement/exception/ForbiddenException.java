/**
 * Forbidden Exception
 *
 * Thrown for unauthorized access attempts (HTTP 403).
 * Used when user lacks permission for requested operation.
 */
package com.example.studentmanagement.exception;

public class ForbiddenException extends BaseException {
    public ForbiddenException(String message) {
        super(message);
    }
}