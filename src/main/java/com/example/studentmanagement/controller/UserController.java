/**
 * User Controller
 *
 * Handles system user management operations (Admin only).
 * Manages user registration, updates, and role-based access control.
 */
package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.ApiResponse;
import com.example.studentmanagement.dto.RegisterRequest;
import com.example.studentmanagement.dto.UpdateUserRequest;
import com.example.studentmanagement.dto.UserResponse;
import com.example.studentmanagement.entity.Role;
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
import java.util.stream.Collectors;

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
     * Register new user - admin only access
     */
    @PostMapping
    @Operation(summary = "Register User", description = "Create new system user (Professor or Admin) - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<User>> registerUser(
            @Valid @RequestBody RegisterRequest request) {
        User user = userService.registerUser(request);
        return new ResponseEntity<>(ApiResponse.created("User registered successfully", user), HttpStatus.CREATED);
    }

    /**
     * Update user information - admin only access
     */
    @PutMapping("/{userId}")
    @Operation(summary = "Update User", description = "Update existing user information - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequest request) {
        User user = userService.updateUser(userId, request);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", user));
    }

    /**
     * Get all users with branch information - admin only access
     */
    @GetMapping
    @Operation(summary = "Get All Users", description = "Retrieve list of all system users with branch information - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        List<UserResponse> userResponses = users.stream()
                .map(user -> {
                    UserResponse response = new UserResponse();
                    response.setUserId(user.getUserId());
                    response.setUsername(user.getUsername());
                    response.setRole(user.getRole());

                    // Add branch information for professors
                    if (user.getRole() == Role.PROFESSOR && user.getBranch() != null) {
                        response.setBranchId(user.getBranch().getBranchId());
                        response.setBranchName(user.getBranch().getName());
                    }

                    return response;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", userResponses));
    }

    /**
     * Get paginated users with branch information - admin only access
     */
    @GetMapping("/paginated")
    @Operation(summary = "Get Users (Paginated)", description = "Retrieve paginated list of system users with branch information - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllUsersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userService.getAllUsers(pageable);

        Page<UserResponse> userResponses = users.map(user -> {
            UserResponse response = new UserResponse();
            response.setUserId(user.getUserId());
            response.setUsername(user.getUsername());
            response.setRole(user.getRole());

            // Add branch information for professors
            if (user.getRole() == Role.PROFESSOR && user.getBranch() != null) {
                response.setBranchId(user.getBranch().getBranchId());
                response.setBranchName(user.getBranch().getName());
            }

            return response;
        });

        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", userResponses));
    }

    /**
     * Get current authenticated user's profile
     */
    @GetMapping("/me")
    @Operation(summary = "Get Current User", description = "Retrieve current authenticated user's profile")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {
        User user = userService.getCurrentUser();

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setUsername(user.getUsername());
        userResponse.setRole(user.getRole());

        // Add branch information for professors
        if (user.getRole() == Role.PROFESSOR && user.getBranch() != null) {
            userResponse.setBranchId(user.getBranch().getBranchId());
            userResponse.setBranchName(user.getBranch().getName());
        }

        return ResponseEntity.ok(ApiResponse.success("Current user retrieved successfully", userResponse));
    }

    /**
     * Get user by ID - admin only access
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Get User by ID", description = "Retrieve specific user details by user ID - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable Long userId) {
        User user = userService.getUserById(userId);

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setUsername(user.getUsername());
        userResponse.setRole(user.getRole());

        // Add branch information for professors
        if (user.getRole() == Role.PROFESSOR && user.getBranch() != null) {
            userResponse.setBranchId(user.getBranch().getBranchId());
            userResponse.setBranchName(user.getBranch().getName());
        }

        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", userResponse));
    }

    /**
     * Delete user - admin only access
     */
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete User", description = "Delete system user by ID - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
    }
}