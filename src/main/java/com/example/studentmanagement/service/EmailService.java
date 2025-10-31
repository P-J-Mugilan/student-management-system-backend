/**
 * Email Service Interface
 * Handles email notifications for system events.
 */
package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Student;

public interface EmailService {

    /**
     * Sends registration confirmation email to student
     */

    void sendStudentRegistrationEmail(Student student);
}