/**
 * Authentication Service Interface

 * Handles user authentication, token generation, and session management.
 * Provides secure access control for the student management system.
 */
package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.JwtResponse;
import com.example.studentmanagement.dto.LoginRequest;
import com.example.studentmanagement.exception.UnauthorizedException;

public interface AuthService {

    /**
     * Authenticates user credentials and returns JWT token with user information.

     * Contract:
     * - Validates username and password against stored credentials
     * - Returns JWT token containing username and role claims
     * - Throws AuthenticationException for invalid credentials
     * - Token expires after configured time period
     *
     * @param request Login credentials (username and password)
     * @return JwtResponse containing token, user info, and role
     */
    JwtResponse login(LoginRequest request) throws UnauthorizedException;

    /**
     * Invalidates user session by blacklisting the JWT token.

     * Contract:
     * - Prevents further use of the provided token
     * - No exception thrown for invalid or already-expired tokens
     * - Immediate session termination for security
     *
     * @param token JWT token to invalidate
     */


    void logout(String token);
}