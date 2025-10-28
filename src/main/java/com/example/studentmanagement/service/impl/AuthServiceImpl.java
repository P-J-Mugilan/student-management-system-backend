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
            System.out.println("🔐 Login attempt for: " + request.getUsername());

            // Authenticate user (works for both Admin and Professor)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Get user from database
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new UnauthorizedException("User not found"));

            // Generate JWT token
            String jwt = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());

            System.out.println("✅ Login successful for: " + user.getUsername() + ", Role: " + user.getRole());

            return new JwtResponse(jwt, user.getUsername(), user.getRole().name());

        } catch (Exception e) {
            System.out.println("❌ Login failed: " + e.getMessage());
            throw new UnauthorizedException("Invalid username or password");
        }
    }

    @Override
    public void logout(String token) {
        try {
            // Get current user before clearing context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication != null ? authentication.getName() : "Unknown";
            String role = authentication != null && authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) ? "ADMIN" : "PROFESSOR";

            System.out.println("👤 Logging out user: " + username + ", Role: " + role);

            if (token != null) {
                // Add token to blacklist
                tokenBlacklist.blacklistToken(token);
                System.out.println("✅ Token blacklisted successfully");
            }

            // Clear security context
            SecurityContextHolder.clearContext();
            System.out.println("✅ Security context cleared");
            System.out.println("🎯 " + role + " user fully logged out");

        } catch (Exception e) {
            System.out.println("❌ Error during logout: " + e.getMessage());
            throw new RuntimeException("Logout failed: " + e.getMessage());
        }
    }
}