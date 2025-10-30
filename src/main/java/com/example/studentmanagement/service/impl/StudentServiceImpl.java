/**
 * Student Service Implementation
 *
 * Handles student management with role-based access control.
 * Admins have full access, professors can only manage students in their branch.
 */
package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.dto.StudentRequest;
import com.example.studentmanagement.entity.Branch;
import com.example.studentmanagement.entity.Role;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.exception.BadRequestException;
import com.example.studentmanagement.exception.ConflictException;
import com.example.studentmanagement.exception.ForbiddenException;
import com.example.studentmanagement.exception.ResourceNotFoundException;
import com.example.studentmanagement.repository.BranchRepository;
import com.example.studentmanagement.repository.StudentRepository;
import com.example.studentmanagement.service.EmailService;
import com.example.studentmanagement.service.StudentService;
import com.example.studentmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final BranchRepository branchRepository;
    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, BranchRepository branchRepository, UserService userService, EmailService emailService) {
        this.studentRepository = studentRepository;
        this.branchRepository = branchRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public Student createStudent(StudentRequest request) {
        User currentUser = userService.getCurrentUser();

        // Check if student email already exists
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Student with email " + request.getEmail() + " already exists");
        }

        // Validate required fields
        if (request.getBranchId() == null) {
            throw new BadRequestException("Branch ID is required for student");
        }

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", request.getBranchId()));

        /**
         * Contract: Strict branch validation for professors
         * - Professors can ONLY create students in their assigned branch
         * - Prevents professors from adding students to other branches
         * - Ensures branch-level data isolation
         */
        if (currentUser.getRole().equals(Role.PROFESSOR)) {
            if (currentUser.getBranch() == null) {
                throw new ForbiddenException("Professor must be assigned to a branch to create students");
            }
            if (!currentUser.getBranch().getBranchId().equals(request.getBranchId())) {
                throw new ForbiddenException("You can only create students in your own branch");
            }
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setAge(request.getAge());
        student.setGender(request.getGender());
        student.setBranch(branch);

        Student savedStudent = studentRepository.save(student);

        // Send email notification to student
        try {
            emailService.sendStudentRegistrationEmail(savedStudent);
        } catch (Exception e) {
            // Log email failure but don't break the registration flow
        }

        return savedStudent;
    }

    @Override
    public List<Student> getAllStudents() {
        User currentUser = userService.getCurrentUser();

        if (currentUser.getRole().equals(Role.ADMIN)) {
            return studentRepository.findAll();
        } else if (currentUser.getRole().equals(Role.PROFESSOR)) {
            if (currentUser.getBranch() == null) {
                throw new ForbiddenException("Professor must be assigned to a branch to view students");
            }
            return studentRepository.findByBranchBranchId(currentUser.getBranch().getBranchId());
        } else {
            throw new ForbiddenException("Access denied");
        }
    }

    @Override
    public Page<Student> getAllStudents(Pageable pageable) {
        User currentUser = userService.getCurrentUser();

        if (currentUser.getRole().equals(Role.ADMIN)) {
            return studentRepository.findAll(pageable);
        } else if (currentUser.getRole().equals(Role.PROFESSOR)) {
            if (currentUser.getBranch() == null) {
                throw new ForbiddenException("Professor must be assigned to a branch to view students");
            }
            return studentRepository.findByBranchBranchId(currentUser.getBranch().getBranchId(), pageable);
        } else {
            throw new ForbiddenException("Access denied");
        }
    }

    @Override
    public Student getStudentById(Long studentId) {
        User currentUser = userService.getCurrentUser();
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        // Check access: Professor can only access students from their branch
        if (currentUser.getRole().equals(Role.PROFESSOR)) {
            if (currentUser.getBranch() == null) {
                throw new ForbiddenException("Professor must be assigned to a branch to access students");
            }
            if (!student.getBranch().getBranchId().equals(currentUser.getBranch().getBranchId())) {
                throw new ForbiddenException("You can only access students from your own branch");
            }
        }

        return student;
    }

    @Override
    public Student updateStudent(Long studentId, StudentRequest request) {
        User currentUser = userService.getCurrentUser();
        Student student = getStudentById(studentId); // This already checks branch access

        // For professors: Remove branchId from request to prevent branch changes
        if (currentUser.getRole().equals(Role.PROFESSOR)) {
            request.setBranchId(null);
        }

        // Update student fields
        if (request.getName() != null) {
            student.setName(request.getName());
        }

        if (request.getEmail() != null && !request.getEmail().equals(student.getEmail())) {
            if (studentRepository.existsByEmail(request.getEmail())) {
                throw new ConflictException("Student with email " + request.getEmail() + " already exists");
            }
            student.setEmail(request.getEmail());
        }

        if (request.getAge() != null && request.getAge() > 0) {
            student.setAge(request.getAge());
        }

        if (request.getGender() != null) {
            student.setGender(request.getGender());
        }

        // Only admin can update branch
        if (request.getBranchId() != null && currentUser.getRole().equals(Role.ADMIN)) {
            Branch branch = branchRepository.findById(request.getBranchId())
                    .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", request.getBranchId()));
            student.setBranch(branch);
        }

        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long studentId) {
        User currentUser = userService.getCurrentUser();
        Student student = getStudentById(studentId);

        // Professors can only delete students in their own branch
        if (currentUser.getRole().equals(Role.PROFESSOR)) {
            if (currentUser.getBranch() == null) {
                throw new ForbiddenException("Professor must be assigned to a branch to delete students");
            }
            if (!student.getBranch().getBranchId().equals(currentUser.getBranch().getBranchId())) {
                throw new ForbiddenException("You can only delete students in your own branch");
            }
        }

        studentRepository.delete(student);
    }

    @Override
    public Student getStudentByEmailPublic(String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "email", email));
    }

    @Override
    public List<Student> getStudentsByBranch(Long branchId) {
        User currentUser = userService.getCurrentUser();

        // Check if branch exists
        boolean branchExists = branchRepository.existsById(branchId);
        if (!branchExists) {
            throw new ResourceNotFoundException("Branch", "id", branchId);
        }

        /**
         * Contract: Professor branch access validation
         * - Professors can only access students from their assigned branch
         * - Prevents professors from viewing students in other branches
         * - Maintains data isolation between branches
         */
        if (currentUser.getRole().equals(Role.PROFESSOR)) {
            if (currentUser.getBranch() == null) {
                throw new ForbiddenException("Professor must be assigned to a branch to view students");
            }
            if (!currentUser.getBranch().getBranchId().equals(branchId)) {
                throw new ForbiddenException("You can only access students from your own branch");
            }
        }

        return studentRepository.findByBranchBranchId(branchId);
    }
}