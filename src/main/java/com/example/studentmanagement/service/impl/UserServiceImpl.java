/**
 * User Service Implementation
 *
 * Handles user management including registration, updates, and role-based access.
 * Manages admin and professor accounts with proper branch assignments.
 */
package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.dto.RegisterRequest;
import com.example.studentmanagement.dto.UpdateUserRequest;
import com.example.studentmanagement.entity.Branch;
import com.example.studentmanagement.entity.Role;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.exception.BadRequestException;
import com.example.studentmanagement.exception.ForbiddenException;
import com.example.studentmanagement.exception.ResourceNotFoundException;
import com.example.studentmanagement.repository.BranchRepository;
import com.example.studentmanagement.repository.UserRepository;
import com.example.studentmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BranchRepository branchRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.branchRepository = branchRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(RegisterRequest request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }

        // Validate role - ONLY ADMIN and PROFESSOR allowed
        if (request.getRole() == null ||
                (request.getRole() != Role.ADMIN && request.getRole() != Role.PROFESSOR)) {
            throw new BadRequestException("Only ADMIN and PROFESSOR roles can be created as users");
        }

        /**
         * Contract: Role-based branch assignment validation
         * - Admin users cannot be assigned to any branch
         * - Professor users must be assigned to exactly one branch
         * - Prevents invalid user-branch relationships
         */
        if (request.getRole() == Role.ADMIN && request.getBranchId() != null) {
            throw new BadRequestException("Admin cannot be assigned to a branch");
        }

        if (request.getRole() == Role.PROFESSOR && request.getBranchId() == null) {
            throw new BadRequestException("Branch ID is required for professor");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        // Set branch for PROFESSOR only
        if (request.getRole() == Role.PROFESSOR && request.getBranchId() != null) {
            Branch branch = branchRepository.findById(request.getBranchId())
                    .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", request.getBranchId()));
            user.setBranch(branch);
        }

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @Override
    public User updateUser(Long userId, UpdateUserRequest request) {
        User existingUser = getUserById(userId);

        if (request.getUsername() != null) {
            existingUser.setUsername(request.getUsername());
        }

        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        existingUser.setRole(request.getRole());

        /*
         * Contract: Dynamic branch management during user updates
         * - Professors must have a branch assignment
         * - Admins must have no branch assignment
         * - Automatically handles branch cleanup on role changes
         */
        if (request.getRole() == Role.PROFESSOR && request.getBranchId() != null) {
            Branch branch = branchRepository.findById(request.getBranchId())
                    .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + request.getBranchId()));
            existingUser.setBranch(branch);
        } else {
            existingUser.setBranch(null);
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User currentUser = getCurrentUser();

        if (!currentUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("Only admin can delete users");
        }

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }

        userRepository.deleteById(userId);
    }
}