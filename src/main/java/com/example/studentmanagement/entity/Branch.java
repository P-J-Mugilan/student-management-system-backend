/**
 * Branch Entity Class
 *
 * <p>Represents an academic branch/department in the student management system.
 * Each branch can have multiple students and professors associated with it.
 * This entity serves as the central organizational unit for grouping students
 * and faculty members.</p>
 *
 * @version 1.0
 * @since 2025
 *
 * @author Student Management System Team
 */
package com.example.studentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA Entity representing the branches table in the database.
 * Maps to the 'branches' table with appropriate relationships to students and professors.
 */
@Entity
@Table(name = "branches")
public class Branch {

    /**
     * Primary key identifier for the branch.
     * Auto-generated unique identifier using database identity column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    /**
     * Unique name of the branch (e.g., "Computer Science", "Electrical Engineering").
     * This field is required and must be unique across all branches.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Description of the branch providing additional details about the department.
     * This field is required to give context about the branch's focus and offerings.
     */
    @Column(nullable = false)
    private String description;

    /**
     * List of students belonging to this branch.
     * Uses bidirectional one-to-many relationship with Student entity.
     * JsonIgnore prevents circular references during JSON serialization.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();

    /**
     * List of professors (users with PROFESSOR role) assigned to this branch.
     * Uses bidirectional one-to-many relationship with User entity.
     * JsonIgnore prevents circular references during JSON serialization.
     */
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> professors = new ArrayList<>();

    /**
     * Default constructor required by JPA specification.
     * Initializes a new instance of the Branch class with default values.
     */
    public Branch() {}

    /**
     * Parameterized constructor for creating a new branch with initial values.
     *
     * @param name The unique name of the branch
     * @param description The descriptive information about the branch
     */
    public Branch(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // ==================== GETTER AND SETTER METHODS ====================

    /**
     * Gets the unique identifier of the branch.
     *
     * @return The branch ID as a Long value
     */
    public Long getBranchId() {
        return branchId;
    }

    /**
     * Sets the unique identifier of the branch.
     * Typically used by JPA and should not be set manually in application code.
     *
     * @param branchId The branch ID to set
     */
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    /**
     * Gets the name of the branch.
     *
     * @return The branch name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the branch.
     *
     * @param name The branch name to set
     * @throws IllegalArgumentException if name is null or empty
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the branch.
     *
     * @return The branch description as a String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the branch.
     *
     * @param description The branch description to set
     * @throws IllegalArgumentException if description is null or empty
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the list of students enrolled in this branch.
     *
     * @return Unmodifiable list of Student entities
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Sets the list of students for this branch.
     * Note: This method should typically be used by JPA and not directly in application logic.
     *
     * @param students The list of Student entities to set
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    /**
     * Gets the list of professors assigned to this branch.
     *
     * @return Unmodifiable list of User entities with PROFESSOR role
     */
    public List<User> getProfessors() {
        return professors;
    }

    /**
     * Sets the list of professors for this branch.
     * Note: This method should typically be used by JPA and not directly in application logic.
     *
     * @param professors The list of User entities to set
     */
    public void setProfessors(List<User> professors) {
        this.professors = professors;
    }

    /**
     * Convenience method to add a student to this branch.
     * Maintains bidirectional relationship consistency.
     *
     * @param student The student to add to this branch
     */
    public void addStudent(Student student) {
        students.add(student);
        student.setBranch(this);
    }

    /**
     * Convenience method to remove a student from this branch.
     * Maintains bidirectional relationship consistency.
     *
     * @param student The student to remove from this branch
     */
    public void removeStudent(Student student) {
        students.remove(student);
        student.setBranch(null);
    }

    /**
     * Convenience method to add a professor to this branch.
     * Maintains bidirectional relationship consistency.
     *
     * @param professor The professor to add to this branch
     */
    public void addProfessor(User professor) {
        professors.add(professor);
        professor.setBranch(this);
    }

    /**
     * Convenience method to remove a professor from this branch.
     * Maintains bidirectional relationship consistency.
     *
     * @param professor The professor to remove from this branch
     */
    public void removeProfessor(User professor) {
        professors.remove(professor);
        professor.setBranch(null);
    }

    /**
     * Returns a string representation of the branch.
     *
     * @return String containing branch ID and name
     */
    @Override
    public String toString() {
        return "Branch{" +
                "branchId=" + branchId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}