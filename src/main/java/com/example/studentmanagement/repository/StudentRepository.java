package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.entity.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Student entities.
 *
 * <p>Supports database queries for student retrieval, validation,
 * branch-specific listings, and pagination for efficient API responses.</p>
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /** Find a student by their unique email address. */
    Optional<Student> findByEmail(String email);

    /** Check if a student with a given email already exists. */
    boolean existsByEmail(String email);

    // List all students in a branch
    List<Student> findByBranchBranchId(Long branchId);

    /** Find all students belonging to a specific branch (paginated). */
    Page<Student> findByBranchBranchId(Long branchId, Pageable pageable);

    /** Get all students with pagination. */
    Page<Student> findAll(Pageable pageable);
}
