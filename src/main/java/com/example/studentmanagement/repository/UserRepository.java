package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository interface for managing User entities.
 *
 * <p>Provides database operations for user authentication, role-based queries,
 * and branch-specific user retrieval. Supports pagination for list operations.</p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /** Find a user by their unique username. */
    Optional<User> findByUsername(String username);

    /** Check if a user exists by username. */
    boolean existsByUsername(String username);

    /** Find users by role with pagination support. */
    Page<User> findByRole(Role role, Pageable pageable);
}
