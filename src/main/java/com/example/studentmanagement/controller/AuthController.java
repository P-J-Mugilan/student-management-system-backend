package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.ApiResponse;
import com.example.studentmanagement.dto.JwtResponse;
import com.example.studentmanagement.dto.LoginRequest;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.exception.ResourceNotFoundException;
import com.example.studentmanagement.repository.UserRepository;
import com.example.studentmanagement.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "1. Authentication", description = "User authentication and authorization endpoints")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    @Operation(summary = "User Login", description = "Authenticate user and return JWT token for accessing protected endpoints")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@Valid @RequestBody LoginRequest request) {
        JwtResponse jwtResponse = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", jwtResponse));
    }

    @PostMapping("/logout")
    @Operation(summary = "User Logout", description = "Logout current user and clear security context")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        authService.logout(token);
        return ResponseEntity.ok(ApiResponse.success("Logout successful. Please remove JWT token from client storage."));
    }

    @GetMapping("/me")
    @Operation(summary = "Get Current User", description = "Get details of currently authenticated user")
    public ResponseEntity<ApiResponse<Object>> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            System.out.println("=== GET CURRENT USER ENDPOINT ===");

            if (isUserAuthenticated(authentication)) {
                String username = authentication.getName();
                System.out.println("✅ User authenticated: " + username);

                // Get full user details from database
                User user = getUserFromDatabase(username);
                if (user != null) {
                    System.out.println("✅ Returning user details for: " + user.getUsername() + ", Role: " + user.getRole());
                    return ResponseEntity.ok(ApiResponse.success("Current user retrieved successfully", user));
                }
            }

            System.out.println("❌ No authenticated user found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("No user currently authenticated. Please login."));

        } catch (Exception e) {
            System.out.println("❌ Error in getCurrentUser: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving current user: " + e.getMessage()));
        }
    }

    private User getUserFromDatabase(String username) {
        try {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        } catch (Exception e) {
            System.out.println("Error fetching user from database: " + e.getMessage());
            return null;
        }
    }

    private boolean isUserAuthenticated(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        // Check if it's an anonymous user
        if (authentication.getPrincipal() instanceof String) {
            String principal = (String) authentication.getPrincipal();
            return !"anonymousUser".equals(principal);
        }

        return true;
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}