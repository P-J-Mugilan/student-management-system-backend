package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.StudentRequest;
import com.example.studentmanagement.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface StudentService {
    Student createStudent(StudentRequest request);
    List<Student> getAllStudents();
    Page<Student> getAllStudents(Pageable pageable);
    Student getStudentById(Long studentId);
    Student updateStudent(Long studentId, StudentRequest request);
    void deleteStudent(Long studentId);
    Student getStudentByEmailPublic(String email); // Public access for students
    List<Student> getStudentsByBranch(Long branchId); // For professors
}