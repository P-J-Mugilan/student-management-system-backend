package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.dto.StudentRequest;
import com.example.studentmanagement.dto.StudentResponse;
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
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final BranchRepository branchRepository;
    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              BranchRepository branchRepository,
                              UserService userService,
                              EmailService emailService) {
        this.studentRepository = studentRepository;
        this.branchRepository = branchRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    /** Maps Student entity → StudentResponse DTO */
    private StudentResponse mapToResponse(Student student) {
        return new StudentResponse(
                student.getStudentId(),
                student.getName(),
                student.getEmail(),
                student.getAge(),
                student.getGender(),
                student.getBranch() != null ? student.getBranch().getBranchId() : null,
                student.getBranch() != null ? student.getBranch().getName() : null
        );
    }

    /** Gets the currently authenticated User entity */
    private User getCurrentUser() {
        return userService.getCurrentUserEntity();
    }

    @Override
    public StudentResponse createStudent(StudentRequest request) {
        User currentUser = getCurrentUser();

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Student with email " + request.getEmail() + " already exists");
        }

        if (request.getBranchId() == null) {
            throw new BadRequestException("Branch ID is required for student");
        }

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", request.getBranchId()));

        if (currentUser.getRole() == Role.PROFESSOR &&
                !currentUser.getBranch().getBranchId().equals(branch.getBranchId())) {
            throw new ForbiddenException("You can only create students in your own branch");
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setAge(request.getAge());
        student.setGender(request.getGender());
        student.setBranch(branch);

        Student saved = studentRepository.save(student);

        try {
            emailService.sendStudentRegistrationEmail(saved);
        } catch (Exception ignored) {}

        return mapToResponse(saved);
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        User currentUser = getCurrentUser();
        List<Student> students;

        if (currentUser.getRole() == Role.ADMIN) {
            students = studentRepository.findAll();
        } else if (currentUser.getRole() == Role.PROFESSOR) {
            students = studentRepository.findByBranchBranchId(currentUser.getBranch().getBranchId());
        } else {
            throw new ForbiddenException("Access denied");
        }

        return students.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<StudentResponse> getAllStudents(Pageable pageable) {
        User currentUser = getCurrentUser();

        if (currentUser.getRole() == Role.ADMIN) {
            return studentRepository.findAll(pageable).map(this::mapToResponse);
        } else if (currentUser.getRole() == Role.PROFESSOR) {
            return studentRepository.findByBranchBranchId(currentUser.getBranch().getBranchId(), pageable)
                    .map(this::mapToResponse);
        } else {
            throw new ForbiddenException("Access denied");
        }
    }

    @Override
    public StudentResponse getStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        User currentUser = getCurrentUser();
        if (currentUser.getRole() == Role.PROFESSOR &&
                !currentUser.getBranch().getBranchId().equals(student.getBranch().getBranchId())) {
            throw new ForbiddenException("You can only access students from your own branch");
        }

        return mapToResponse(student);
    }

    @Override
    public StudentResponse updateStudent(Long studentId, StudentRequest request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        User currentUser = getCurrentUser();
        if (currentUser.getRole() == Role.PROFESSOR) {
            request.setBranchId(null); // Professors cannot change branch
        }

        if (request.getName() != null) student.setName(request.getName());

        if (request.getEmail() != null && !request.getEmail().equals(student.getEmail())) {
            if (studentRepository.existsByEmail(request.getEmail())) {
                throw new ConflictException("Student with email " + request.getEmail() + " already exists");
            }
            student.setEmail(request.getEmail());
        }

        if (request.getAge() != null) student.setAge(request.getAge());
        if (request.getGender() != null) student.setGender(request.getGender());

        if (request.getBranchId() != null && currentUser.getRole() == Role.ADMIN) {
            Branch branch = branchRepository.findById(request.getBranchId())
                    .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", request.getBranchId()));
            student.setBranch(branch);
        }

        return mapToResponse(studentRepository.save(student));
    }

    @Override
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        User currentUser = getCurrentUser();
        if (currentUser.getRole() == Role.PROFESSOR &&
                !currentUser.getBranch().getBranchId().equals(student.getBranch().getBranchId())) {
            throw new ForbiddenException("You can only delete students in your own branch");
        }

        studentRepository.delete(student);
    }

    @Override
    public StudentResponse getStudentByEmailPublic(String email) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "email", email));
        return mapToResponse(student);
    }

    @Override
    public List<StudentResponse> getStudentsByBranch(Long branchId) {
        if (!branchRepository.existsById(branchId)) {
            throw new ResourceNotFoundException("Branch", "id", branchId);
        }

        User currentUser = getCurrentUser();
        if (currentUser.getRole() == Role.PROFESSOR &&
                !currentUser.getBranch().getBranchId().equals(branchId)) {
            throw new ForbiddenException("You can only access students from your own branch");
        }

        return studentRepository.findByBranchBranchId(branchId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
