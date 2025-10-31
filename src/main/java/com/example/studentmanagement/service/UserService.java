package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.RegisterRequest;
import com.example.studentmanagement.dto.UpdateUserRequest;
import com.example.studentmanagement.dto.UserResponse;
import com.example.studentmanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    /** Register a new user (ADMIN only) */
    UserResponse registerUser(RegisterRequest request);

    /** Retrieve all users (ADMIN only) */
    List<UserResponse> getAllUsers();

    /** Retrieve paginated users (ADMIN only) */
    Page<UserResponse> getAllUsers(Pageable pageable);

    /** Get current authenticated user (DTO) */
    UserResponse getCurrentUser();

    /** Get current authenticated user entity (for internal service use) */
    User getCurrentUserEntity();

    /** Get a specific user by ID (ADMIN only) */
    UserResponse getUserById(Long userId);

    /** Update a user (ADMIN only) */
    UserResponse updateUser(Long userId, UpdateUserRequest request);

    /** Delete a user (ADMIN only) */
    void deleteUser(Long userId);
}
