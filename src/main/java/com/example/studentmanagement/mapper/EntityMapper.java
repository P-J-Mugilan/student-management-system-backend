package com.example.studentmanagement.mapper;

import com.example.studentmanagement.dto.*;
import com.example.studentmanagement.entity.*;

/**
 * Unified Mapper class for converting entities to DTOs.
 * Supports User, Student, and Branch entities for API responses.
 *
 * @version 1.0
 * @since 2025
 *
 * Author: Student Management System Team
 */
public class EntityMapper {

    // ==================== USER ====================

    public UserResponse toUserResponse(User user) {
        if (user == null) return null;

        Long branchId = user.getBranch() != null ? user.getBranch().getBranchId() : null;
        String branchName = user.getBranch() != null ? user.getBranch().getName() : null;

        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getRole(),
                branchId,
                branchName
        );
    }

    // ==================== STUDENT ====================

    public static StudentResponse toStudentResponse(Student student) {
        if (student == null) return null;

        Long branchId = student.getBranch() != null ? student.getBranch().getBranchId() : null;
        String branchName = student.getBranch() != null ? student.getBranch().getName() : null;

        return new StudentResponse(
                student.getStudentId(),
                student.getName(),
                student.getEmail(),
                student.getAge(),
                student.getGender(),
                branchId,
                branchName
        );
    }

    // ==================== BRANCH ====================

    public static BranchResponse toBranchResponse(Branch branch) {
        if (branch == null) return null;

        BranchResponse response = new BranchResponse(branch);
        response.setBranchId(branch.getBranchId());
        response.setName(branch.getName());
        response.setDescription(branch.getDescription());
        response.setStudentCount(branch.getStudents() != null ? branch.getStudents().size() : 0);
        response.setProfessorCount(branch.getProfessors() != null ? branch.getProfessors().size() : 0);

        return response;
    }

}
