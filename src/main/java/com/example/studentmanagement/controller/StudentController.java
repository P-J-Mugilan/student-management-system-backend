package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.ApiResponse;
import com.example.studentmanagement.dto.StudentRequest;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.service.StudentService;
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
 * Student Management API - Handle student operations (Admin & Professor access)
 * Base URL: /api/students
 */
@RestController
@RequestMapping("/api/students")
@Tag(name = "4. Student Management", description = "Student management endpoints (Admin & Professor access)")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ==================== PUBLIC ENDPOINTS ====================

    /**
     * Get Student by Email (Public)
     * Retrieve student information by email address - No authentication required
     */
    @GetMapping("/public/email/{email}")
    @Operation(summary = "Get Student by Email (Public)", description = "Retrieve student information by email address - NO AUTHENTICATION REQUIRED")
    public ResponseEntity<ApiResponse<Student>> getStudentByEmail(
            @PathVariable String email) {
        Student student = studentService.getStudentByEmailPublic(email);
        return ResponseEntity.ok(ApiResponse.success("Student details retrieved successfully", student));
    }

    // ==================== PROTECTED ENDPOINTS ====================

    /**
     * Create New Student
     * Register new student in the system (Admin & Professor)
     */
    @PostMapping
    @Operation(summary = "Create Student", description = "Register new student in the system - ADMIN OR PROFESSOR ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<Student>> createStudent(
            @Valid @RequestBody StudentRequest request) {
        Student student = studentService.createStudent(request);
        return new ResponseEntity<>(ApiResponse.created("Student created successfully", student), HttpStatus.CREATED);
    }

    /**
     * Get All Students
     * Retrieve list of all students (Admin: all students, Professor: their branch only)
     */
    @GetMapping
    @Operation(summary = "Get All Students", description = "Retrieve list of students (Admin: all, Professor: their branch only)")
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.success("Students retrieved successfully", students));
    }

    /**
     * Get All Students (Paginated)
     * Retrieve paginated list of students (Admin: all students, Professor: their branch only)
     */
    @GetMapping("/paginated")
    @Operation(summary = "Get Students (Paginated)", description = "Retrieve paginated list of students (Admin: all, Professor: their branch only)")
    public ResponseEntity<ApiResponse<Page<Student>>> getAllStudentsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> students = studentService.getAllStudents(pageable);
        return ResponseEntity.ok(ApiResponse.success("Students retrieved successfully", students));
    }

    /**
     * Get Student by ID
     * Retrieve specific student details by student ID
     */
    @GetMapping("/{studentId}")
    @Operation(summary = "Get Student by ID", description = "Retrieve specific student details by student ID")
    public ResponseEntity<ApiResponse<Student>> getStudentById(
            @PathVariable Long studentId) {
        Student student = studentService.getStudentById(studentId);
        return ResponseEntity.ok(ApiResponse.success("Student retrieved successfully", student));
    }

    /**
     * Get Students by Branch
     * Retrieve all students belonging to a specific branch
     */
    @GetMapping("/branch/{branchId}")
    @Operation(summary = "Get Students by Branch", description = "Retrieve all students belonging to a specific branch")
    public ResponseEntity<ApiResponse<List<Student>>> getStudentsByBranch(
            @PathVariable Long branchId) {
        List<Student> students = studentService.getStudentsByBranch(branchId);
        return ResponseEntity.ok(ApiResponse.success("Students retrieved successfully", students));
    }

    /**
     * Update Student
     * Update existing student information
     */
    @PutMapping("/{studentId}")
    @Operation(summary = "Update Student", description = "Update existing student information")
    public ResponseEntity<ApiResponse<Student>> updateStudent(
            @PathVariable Long studentId,
            @Valid @RequestBody StudentRequest request) {
        Student student = studentService.updateStudent(studentId, request);
        return ResponseEntity.ok(ApiResponse.success("Student updated successfully", student));
    }

    /**
     * Delete Student
     * Remove student from the system
     */
    @DeleteMapping("/{studentId}")
    @Operation(summary = "Delete Student", description = "Remove student from the system")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(
            @PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok(ApiResponse.success("Student deleted successfully"));
    }
}