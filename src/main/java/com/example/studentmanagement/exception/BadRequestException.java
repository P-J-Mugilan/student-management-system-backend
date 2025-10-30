/**
 * Bad Request Exception
 *
 * Thrown for invalid client requests (HTTP 400).
 * Used when request data fails validation or business rules.
 */
package com.example.studentmanagement.exception;

public class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        super(message);
    }
}