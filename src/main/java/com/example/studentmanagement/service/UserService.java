package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.RegisterRequest;
import com.example.studentmanagement.dto.UpdateUserRequest;
import com.example.studentmanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserService {
    User registerUser(RegisterRequest request);
    List<User> getAllUsers();
    Page<User> getAllUsers(Pageable pageable);
    User getCurrentUser();
    User getUserById(Long userId);
    User updateUser(Long userId, UpdateUserRequest request);
    void deleteUser(Long userId);
}