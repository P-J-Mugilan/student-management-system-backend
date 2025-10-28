package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.JwtResponse;
import com.example.studentmanagement.dto.LoginRequest;

public interface AuthService {
    JwtResponse login(LoginRequest request);
    void logout(String token);
}