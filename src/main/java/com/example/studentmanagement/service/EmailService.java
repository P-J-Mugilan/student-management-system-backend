package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Student;

public interface EmailService {
    void sendStudentRegistrationEmail(Student student);
}