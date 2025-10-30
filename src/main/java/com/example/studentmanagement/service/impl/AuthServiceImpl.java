/**
 * Authentication Service Implementation

 * Handles user authentication using Spring Security and JWT tokens.
 * Manages login/logout operations with token blacklisting.
 */
package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.dto.JwtResponse;
import com.example.studentmanagement.dto.LoginRequest;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.exception.UnauthorizedException;
import com.example.studentmanagement.repository.UserRepository;
import com.example.studentmanagement.security.JwtUtil;
import com.example.studentmanagement.security.TokenBlacklist;
import com.example.studentmanagement.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TokenBlacklist tokenBlacklist;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                           UserRepository userRepository, TokenBlacklist tokenBlacklist) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.tokenBlacklist = tokenBlacklist;
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        try {
            // Authenticate user credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Get user details from database
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new UnauthorizedException("User not found"));

            // Generate JWT token
            String jwt = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());

            return new JwtResponse(jwt, user.getUsername(), user.getRole().name());

        } catch (Exception e) {
            throw new UnauthorizedException("Invalid username or password");
        }
    }

    @Override
    public void logout(String token) {
        try {
            // Blacklist token to prevent reuse
            if (token != null) {
                tokenBlacklist.blacklistToken(token);
            }

            // Clear security context
            SecurityContextHolder.clearContext();

        } catch (Exception e) {
            throw new RuntimeException("Logout failed: " + e.getMessage());
        }
    }
}