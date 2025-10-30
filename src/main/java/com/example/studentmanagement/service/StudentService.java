/**
 * Student Service Interface

 * Manages student operations including CRUD, search, and branch-specific access.
 * Supports both administrative and professor-level student management.
 */
package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.StudentRequest;
import com.example.studentmanagement.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface StudentService {

    /**
     * Creates a new student with validation and email notification
     */
    Student createStudent(StudentRequest request);

    /**
     * Retrieves all students in the system (admin only)
     */
    List<Student> getAllStudents();

    /**
     * Retrieves students with pagination support
     */
    Page<Student> getAllStudents(Pageable pageable);

    /**
     * Finds a specific student by ID
     */
    Student getStudentById(Long studentId);

    /**
     * Updates student information
     */
    Student updateStudent(Long studentId, StudentRequest request);

    /**
     * Deletes a student record
     */
    void deleteStudent(Long studentId);

    /**
     * Public email search for student lookup

     * Contract:
     * - Available without authentication
     * - Returns limited student information only
     * - Used for public student search functionality
     */
    Student getStudentByEmailPublic(String email);

    /**
     * Gets students by specific branch (professor access)

     * Contract:
     * - Restricted to professors assigned to the branch
     * - Returns only students from professor's assigned branch
     * - Used in professor dashboard for branch-specific management
     */
    List<Student> getStudentsByBranch(Long branchId);
}