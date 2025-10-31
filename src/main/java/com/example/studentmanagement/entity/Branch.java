/**
 * Branch Entity Class
 *
 * <p>Represents an academic branch/department in the student management system.
 * Each branch can have multiple students and professors associated with it.
 * Serves as the central organizational unit for grouping students and faculty members.</p>
 *
 * @version 1.0
 * @since 2025
 */
package com.example.studentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "branches")
public class Branch {

    /** Primary key identifier for the branch. Auto-generated. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    /** Unique name of the branch (e.g., "Computer Science"). Required. */
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Branch name is required")
    private String name;

    /** Description providing details about the branch. Required. */
    @Column(nullable = false)
    @NotBlank(message = "Branch description is required")
    private String description;

    /** Students assigned to this branch. Lazy-loaded. */
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private final List<Student> students = new ArrayList<>();

    /** Professors assigned to this branch. Lazy-loaded. */
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private final List<User> professors = new ArrayList<>();

    /** Default constructor for JPA. */
    public Branch() {}

    /**
     * Constructor with initial values.
     *
     * @param name Branch name
     * @param description Branch description
     */
    public Branch(String name, String description) {
        this.name = name != null ? name.trim() : null;
        this.description = description != null ? description.trim() : null;
    }

    // ==================== GETTERS & SETTERS ====================

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description.trim() : null;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<User> getProfessors() {
        return professors;
    }

    // ==================== HELPER METHODS ====================

    /** Adds a student and maintains bidirectional relationship. */
    public void addStudent(Student student) {
        students.add(student);
        student.setBranch(this);
    }

    /** Removes a student and maintains bidirectional relationship. */
    public void removeStudent(Student student) {
        students.remove(student);
        student.setBranch(null);
    }

    /** Adds a professor and maintains bidirectional relationship. */
    public void addProfessor(User professor) {
        professors.add(professor);
        professor.setBranch(this);
    }

    /** Removes a professor and maintains bidirectional relationship. */
    public void removeProfessor(User professor) {
        professors.remove(professor);
        professor.setBranch(null);
    }

    @Override
    public String toString() {
        return "Branch{" +
                "branchId=" + branchId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
