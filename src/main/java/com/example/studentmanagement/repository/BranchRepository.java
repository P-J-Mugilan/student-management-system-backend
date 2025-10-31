package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository interface for managing Branch entities.
 *
 * <p>Handles database operations for branch records including lookups by name,
 * pagination, and uniqueness validation during creation or update.</p>
 */
@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    /** Find a branch by its unique name. */
    Optional<Branch> findByName(String name);

    /** Check if a branch with a given name already exists. */
    boolean existsByName(String name);

    /** Get all branches with pagination. */
    Page<Branch> findAll(Pageable pageable);


}
