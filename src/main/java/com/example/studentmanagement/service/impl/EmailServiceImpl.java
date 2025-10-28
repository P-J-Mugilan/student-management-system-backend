package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    // Constructor-based dependency injection
    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendStudentRegistrationEmail(Student student) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(student.getEmail());
            message.setSubject("Welcome to Student Management System");
            message.setText("Dear " + student.getName() + ",\n\n" +
                    "You have been successfully registered in the Student Management System.\n" +
                    "Your details:\n" +
                    "Name: " + student.getName() + "\n" +
                    "Email: " + student.getEmail() + "\n" +
                    "Age: " + student.getAge() + "\n" +
                    "Gender: " + student.getGender() + "\n" +
                    "Branch: " + student.getBranch().getName() + "\n\n" +
                    "You can view your details anytime at: http://localhost:8080/api/v1/students/me?email=" + student.getEmail() + "\n\n" +
                    "Thank you for joining us!");

            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}