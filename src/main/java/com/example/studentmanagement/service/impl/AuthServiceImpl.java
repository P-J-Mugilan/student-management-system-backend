package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.dto.JwtResponse;
import com.example.studentmanagement.dto.LoginRequest;
import com.example.studentmanagement.entity.Role;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.exception.UnauthorizedException;
import com.example.studentmanagement.repository.UserRepository;
import com.example.studentmanagement.security.JwtUtil;
import com.example.studentmanagement.security.TokenBlacklist;
import com.example.studentmanagement.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

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
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

            // Ensure principal is UserDetails
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String jwt = jwtUtil.generateToken(userDetails);

            logger.info("User {} logged in successfully", user.getUsername());

            if((Role.PROFESSOR).equals(user.getRole())) {
                return new JwtResponse(jwt, user.getUsername(), user.getRole().name(),user.getBranch().getBranchId(),user.getBranch().getName());
            }
            return new JwtResponse(jwt, user.getUsername(), user.getRole().name());

        } catch (Exception e) {
            logger.warn("Login failed for username {}: {}", request.getUsername(), e.getMessage());
            throw new UnauthorizedException("Invalid username or password");
        }
    }

    @Override
    public void logout(String token) {
        try {
            if (token != null) {
                tokenBlacklist.blacklistToken(token);
                logger.info("Token blacklisted successfully");
            }
            SecurityContextHolder.clearContext();
        } catch (Exception e) {
            logger.error("Logout failed: {}", e.getMessage());
            throw new RuntimeException("Logout failed");
        }
    }
}
