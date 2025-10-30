/**
 * Student Repository

 * Data access layer for Student entity operations.
 * Provides methods for student lookup, email checks, and branch-based queries.
 */
package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Find student by email (used for public lookup)
     */
    Optional<Student> findByEmail(String email);

    /**
     * Check if email already exists (for duplicate prevention)
     */
    boolean existsByEmail(String email);

    /**
     * Find all students by branch ID (for professor access)
     */
    List<Student> findByBranchBranchId(Long branchId);

    /**
     * Find students by branch ID with pagination
     */
    Page<Student> findByBranchBranchId(Long branchId, Pageable pageable);
}