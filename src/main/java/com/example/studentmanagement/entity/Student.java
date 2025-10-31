/**
 * Student Entity Class
 *
 * <p>Represents a student in the Student Management System. This entity stores
 * essential personal and academic information, including branch assignment.
 * Students are associated with branches and managed by professors within their departments.</p>
 *
 * @version 1.0
 * @since 2025
 */
package com.example.studentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * JPA Entity representing the students table.
 * Maps student records to their personal information and branch association.
 */
@Entity
@Table(name = "students")
public class Student {

    /**
     * Primary key identifier for the student.
     * Auto-generated unique identifier using database identity column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    /**
     * Full name of the student.
     * Required field.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Unique email address of the student.
     * Required field and used as a unique identifier.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Age of the student in years.
     * Must be a positive integer.
     */
    @Column(nullable = false)
    private Integer age;

    /**
     * Gender of the student.
     * Uses the Gender enumeration.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    /**
     * Academic branch associated with the student.
     * Each student must belong to one branch.
     * Prevents circular references during JSON serialization.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonIgnoreProperties({"professors", "students", "hibernateLazyInitializer", "handler"})
    private Branch branch;

    /**
     * Default constructor required by JPA.
     */
    public Student() {}

    /**
     * Parameterized constructor for creating a student with full details.
     *
     * @param name   Full name of the student
     * @param email  Unique email address
     * @param age    Age in years
     * @param gender Gender of the student
     * @param branch Academic branch
     */
    public Student(String name, String email, Integer age, Gender gender, Branch branch) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.branch = branch;
    }

    // ==================== GETTERS AND SETTERS ====================

    public Long getStudentId() { return studentId; }

    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }

    public void setAge(Integer age) { this.age = age; }

    public Gender getGender() { return gender; }

    public void setGender(Gender gender) { this.gender = gender; }

    public Branch getBranch() { return branch; }

    public void setBranch(Branch branch) { this.branch = branch; }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", branch=" + (branch != null ? branch.getName() : "null") +
                '}';
    }
}
