/**
 * Branch Repository

 * Data access layer for Branch entity operations.
 * Provides methods for branch lookup and name validation.
 */
package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    /**
     * Check if branch name already exists (for duplicate prevention)
     */
    boolean existsByName(String name);
}