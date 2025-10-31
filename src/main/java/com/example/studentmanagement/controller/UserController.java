package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.ApiResponse;
import com.example.studentmanagement.dto.RegisterRequest;
import com.example.studentmanagement.dto.UpdateUserRequest;
import com.example.studentmanagement.dto.UserResponse;
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
 * User Controller
 *
 * Handles system user management endpoints.
 * Admin-only access for create, update, delete operations.
 * Supports retrieving all users, paginated users, and current authenticated user profile.
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

    @PostMapping
    @Operation(summary = "Register User", description = "Create new system user (Professor or Admin) - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(
            @Valid @RequestBody RegisterRequest request) {
        UserResponse response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("User registered successfully", response));
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update User", description = "Update existing user information - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequest request) {
        UserResponse response = userService.updateUser(userId, request);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", response));
    }

    @GetMapping
    @Operation(summary = "Get All Users", description = "Retrieve list of all users with branch info - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get Users (Paginated)", description = "Retrieve paginated list of users with branch info - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllUsersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponse> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
    }

    @GetMapping("/me")
    @Operation(summary = "Get Current User", description = "Retrieve current authenticated user's profile")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {
        UserResponse response = userService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.success("Current user retrieved successfully", response));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get User by ID", description = "Retrieve specific user details by ID - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long userId) {
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", response));
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete User", description = "Delete system user by ID - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
    }
}
