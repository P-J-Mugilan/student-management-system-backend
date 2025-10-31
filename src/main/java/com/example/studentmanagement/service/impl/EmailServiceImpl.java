package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;

    @Value("${app.url}")
    private String appUrl; // Use application.properties or application.yml

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendStudentRegistrationEmail(Student student) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(student.getEmail());
            message.setFrom("noreply@studentsystem.com");
            message.setSubject("Welcome to Student Management System");
            message.setText(buildEmailContent(student));

            mailSender.send(message);
            logger.info("Registration email sent to {}", student.getEmail());
        } catch (MailException e) {
            logger.error("Failed to send registration email to {}: {}", student.getEmail(), e.getMessage());
            // Do not throw exception to avoid breaking registration flow
        }
    }

    private String buildEmailContent(Student student) {
        return String.format(
                "Dear %s,\n\n" +
                        "You have been successfully registered in the Student Management System.\n" +
                        "Your details:\n" +
                        "Name: %s\n" +
                        "Email: %s\n" +
                        "Age: %d\n" +
                        "Gender: %s\n" +
                        "Branch: %s\n\n" +
                        "You can view your details anytime at: %s/api/v1/students/me?email=%s\n\n" +
                        "Thank you for joining us!",
                student.getName(),
                student.getName(),
                student.getEmail(),
                student.getAge(),
                student.getGender(),
                student.getBranch() != null ? student.getBranch().getName() : "N/A",
                appUrl,
                student.getEmail()
        );
    }
}
