/**
 * Unauthorized Exception
 *
 * Thrown for authentication failures (HTTP 401).
 * Used when user credentials are invalid or missing.
 */
package com.example.studentmanagement.exception;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException(String message) {
        super(message);
    }
}