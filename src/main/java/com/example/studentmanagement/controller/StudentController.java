package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.ApiResponse;
import com.example.studentmanagement.dto.StudentRequest;
import com.example.studentmanagement.dto.StudentResponse;
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
 * Student Controller
 *
 * Handles student management operations with role-based access.
 * Public endpoints for student lookup, protected endpoints for management.
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

    @GetMapping("/public/email/{email}")
    @Operation(summary = "Get Student by Email (Public)", description = "Retrieve student information by email address - NO AUTHENTICATION REQUIRED")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentByEmail(
            @PathVariable String email) {
        StudentResponse student = studentService.getStudentByEmailPublic(email);
        return ResponseEntity.ok(ApiResponse.success("Student details retrieved successfully", student));
    }

    // ==================== PROTECTED ENDPOINTS ====================

    @PostMapping
    @Operation(summary = "Create Student", description = "Register new student in the system - ADMIN OR PROFESSOR ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<StudentResponse>> createStudent(
            @Valid @RequestBody StudentRequest request) {
        StudentResponse student = studentService.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Student created successfully", student));
    }

    @GetMapping
    @Operation(summary = "Get All Students", description = "Retrieve list of students (Admin: all, Professor: their branch only)")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        List<StudentResponse> students = studentService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.success("Students retrieved successfully", students));
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get Students (Paginated)", description = "Retrieve paginated list of students (Admin: all, Professor: their branch only)")
    public ResponseEntity<ApiResponse<Page<StudentResponse>>> getAllStudentsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentResponse> students = studentService.getAllStudents(pageable);
        return ResponseEntity.ok(ApiResponse.success("Students retrieved successfully", students));
    }

    @GetMapping("/{studentId}")
    @Operation(summary = "Get Student by ID", description = "Retrieve specific student details by student ID")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentById(
            @PathVariable Long studentId) {
        StudentResponse student = studentService.getStudentById(studentId);
        return ResponseEntity.ok(ApiResponse.success("Student retrieved successfully", student));
    }

    @GetMapping("/branch/{branchId}")
    @Operation(summary = "Get Students by Branch", description = "Retrieve all students belonging to a specific branch")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getStudentsByBranch(
            @PathVariable Long branchId) {
        List<StudentResponse> students = studentService.getStudentsByBranch(branchId);
        return ResponseEntity.ok(ApiResponse.success("Students retrieved successfully", students));
    }

    @PutMapping("/{studentId}")
    @Operation(summary = "Update Student", description = "Update existing student information")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudent(
            @PathVariable Long studentId,
            @Valid @RequestBody StudentRequest request) {
        StudentResponse student = studentService.updateStudent(studentId, request);
        return ResponseEntity.ok(ApiResponse.success("Student updated successfully", student));
    }

    @DeleteMapping("/{studentId}")
    @Operation(summary = "Delete Student", description = "Remove student from the system")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok(ApiResponse.success("Student deleted successfully"));
    }
}
