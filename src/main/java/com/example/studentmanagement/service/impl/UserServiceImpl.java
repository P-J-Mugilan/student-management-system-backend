package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.dto.RegisterRequest;
import com.example.studentmanagement.dto.UpdateUserRequest;
import com.example.studentmanagement.dto.UserResponse;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_ADMIN = "admin";

    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BranchRepository branchRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.branchRepository = branchRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /** Maps User entity → UserResponse DTO */
    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        if (user.getRole() == Role.PROFESSOR && user.getBranch() != null) {
            response.setBranchId(user.getBranch().getBranchId());
            response.setBranchName(user.getBranch().getName());
        }
        return response;
    }

    /** Get the current authenticated User entity */
    public User getCurrentUserEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }

    @Override
    public UserResponse registerUser(RegisterRequest request) {
        User currentUser = getCurrentUserEntity();
        if (!currentUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("Only admin can register users");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }

        if (request.getRole() == null ||
                (request.getRole() != Role.ADMIN && request.getRole() != Role.PROFESSOR)) {
            throw new BadRequestException("Only ADMIN and PROFESSOR roles are allowed");
        }

        if (request.getRole() == Role.ADMIN && request.getBranchId() != null) {
            throw new BadRequestException("Admin cannot be assigned to a branch");
        }

        if (request.getRole() == Role.PROFESSOR && request.getBranchId() == null) {
            throw new BadRequestException("Professor must have a branch assigned");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        if (request.getRole() == Role.PROFESSOR) {
            Branch branch = branchRepository.findById(request.getBranchId())
                    .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", request.getBranchId()));
            user.setBranch(branch);
        }

        return mapToResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> !DEFAULT_ADMIN.equals("admin"))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);

        // Filter out default admin and map to UserResponse
        List<UserResponse> filtered = usersPage.getContent().stream()
                .filter(user -> !DEFAULT_ADMIN.equalsIgnoreCase(user.getUsername()))
                .map(this::mapToResponse)
                .toList();

        // Return a new PageImpl with filtered content
        return new PageImpl<>(filtered, pageable, usersPage.getTotalElements() - (usersPage.getContent().size() - filtered.size()));
    }

    @Override
    public UserResponse getCurrentUser() {
        return mapToResponse(getCurrentUserEntity());
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return mapToResponse(user);
    }

    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (request.getUsername() != null) existingUser.setUsername(request.getUsername());
        if (request.getPassword() != null && !request.getPassword().trim().isEmpty())
            existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getRole() != null) existingUser.setRole(request.getRole());

        if (request.getRole() == Role.PROFESSOR && request.getBranchId() != null) {
            Branch branch = branchRepository.findById(request.getBranchId())
                    .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", request.getBranchId()));
            existingUser.setBranch(branch);
        } else if (request.getRole() == Role.ADMIN) {
            existingUser.setBranch(null);
        }

        return mapToResponse(userRepository.save(existingUser));
    }

    @Override
    public void deleteUser(Long userId) {
        User currentUser = getCurrentUserEntity();
        if (!currentUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("Only admin can delete users");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userRepository.delete(user);
    }
}
