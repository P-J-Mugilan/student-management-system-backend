package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.StudentRequest;
import com.example.studentmanagement.dto.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    StudentResponse createStudent(StudentRequest request);

    List<StudentResponse> getAllStudents();

    Page<StudentResponse> getAllStudents(Pageable pageable);

    StudentResponse getStudentById(Long studentId);

    StudentResponse updateStudent(Long studentId, StudentRequest request);

    void deleteStudent(Long studentId);

    StudentResponse getStudentByEmailPublic(String email);

    List<StudentResponse> getStudentsByBranch(Long branchId);
}
