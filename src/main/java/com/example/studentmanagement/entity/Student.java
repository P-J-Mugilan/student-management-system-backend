/**
 * Student Entity Class
 *
 * <p>Represents a student in the student management system. This entity stores
 * essential student information including personal details and academic branch
 * assignment. Students are associated with branches and managed by professors
 * within their respective departments.</p>
 *
 * @version 1.0
 * @since 2024
 *
 * @author Student Management System Team
 */
package com.example.studentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * JPA Entity representing the students table in the database.
 * Maps student records with their personal information and branch associations.
 * Maintains relationships with Branch entity for organizational structure.
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
     * This field is required and stores the complete name of the student.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Unique email address of the student.
     * Serves as a unique identifier and contact method. Required field.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Age of the student in years.
     * Must be a positive integer representing the student's age.
     */
    @Column(nullable = false)
    private Integer age;

    /**
     * Gender identity of the student.
     * Uses the Gender enumeration to ensure data consistency.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    /**
     * Academic branch to which the student belongs.
     * Many-to-one relationship with Branch entity. Each student must be
     * associated with one branch. JsonIgnoreProperties prevents circular
     * references during JSON serialization.
     */
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonIgnoreProperties({"professors", "students"})
    private Branch branch;

    /**
     * Default constructor required by JPA specification.
     * Initializes a new instance of the Student class.
     */
    public Student() {}

    /**
     * Parameterized constructor for creating a new student with complete information.
     *
     * @param name The full name of the student
     * @param email The unique email address of the student
     * @param age The age of the student in years
     * @param gender The gender identity of the student
     * @param branch The academic branch to which the student belongs
     */
    public Student(String name, String email, Integer age, Gender gender, Branch branch) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.branch = branch;
    }

    // ==================== GETTER AND SETTER METHODS ====================

    /**
     * Gets the unique identifier of the student.
     *
     * @return The student ID as a Long value
     */
    public Long getStudentId() {
        return studentId;
    }

    /**
     * Sets the unique identifier of the student.
     * Typically used by JPA and should not be set manually in application code.
     *
     * @param studentId The student ID to set
     */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    /**
     * Gets the full name of the student.
     *
     * @return The student's name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the full name of the student.
     *
     * @param name The student name to set
     * @throws IllegalArgumentException if name is null or empty
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email address of the student.
     *
     * @return The student's email as a String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the student.
     * Must be unique across all students in the system.
     *
     * @param email The student email to set
     * @throws IllegalArgumentException if email is null, empty, or not unique
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the age of the student.
     *
     * @return The student's age as an Integer
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Sets the age of the student.
     * Should be a reasonable age value for educational purposes.
     *
     * @param age The student age to set
     * @throws IllegalArgumentException if age is null or outside valid range
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Gets the gender of the student.
     *
     * @return The student's gender as a Gender enum value
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the gender of the student.
     *
     * @param gender The student gender to set
     * @throws IllegalArgumentException if gender is null
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Gets the branch to which the student belongs.
     *
     * @return The Branch entity associated with this student
     */
    public Branch getBranch() {
        return branch;
    }

    /**
     * Sets the branch for the student.
     * Establishes the many-to-one relationship with Branch entity.
     *
     * @param branch The Branch entity to associate with this student
     * @throws IllegalArgumentException if branch is null
     */
    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    /**
     * Returns a string representation of the student.
     * Useful for debugging and logging purposes.
     *
     * @return String containing student ID, name, and email
     */
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