package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.ApiResponse;
import com.example.studentmanagement.dto.RegisterRequest;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User Management API - Handle system user operations (Admin only)
 * Base URL: /api/users
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "3. User Management", description = "System user management endpoints (Admin access only)")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Register New User
     * Create new system user (Professor or Admin) - Admin only
     */
    @PostMapping
    @Operation(summary = "Register User", description = "Create new system user (Professor or Admin) - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<User>> registerUser(
            @Valid @RequestBody RegisterRequest request) {
        User user = userService.registerUser(request);
        return new ResponseEntity<>(ApiResponse.created("User registered successfully", user), HttpStatus.CREATED);
    }

    /**
     * Get All Users
     * Retrieve list of all system users (Admin only)
     */
    @GetMapping
    @Operation(summary = "Get All Users", description = "Retrieve list of all system users - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
    }

    /**
     * Get All Users (Paginated)
     * Retrieve paginated list of system users (Admin only)
     */
    @GetMapping("/paginated")
    @Operation(summary = "Get Users (Paginated)", description = "Retrieve paginated list of system users - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<Page<User>>> getAllUsersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
    }

    /**
     * Get Current User Profile
     * Retrieve current authenticated user's profile
     */
    @GetMapping("/me")
    @Operation(summary = "Get Current User", description = "Retrieve current authenticated user's profile")
    public ResponseEntity<ApiResponse<User>> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.success("Current user retrieved successfully", user));
    }

    /**
     * Get User by ID
     * Retrieve specific user details by user ID (Admin only)
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Get User by ID", description = "Retrieve specific user details by user ID - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<User>> getUserById(
            @PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", user));
    }

    /**
     * Delete User
     * Delete system user by ID (Admin only)
     */
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete User", description = "Delete system user by ID - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
    }
}