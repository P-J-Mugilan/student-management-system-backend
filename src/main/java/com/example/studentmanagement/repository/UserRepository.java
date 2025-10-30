/**
 * User Repository

 * Data access layer for User entity operations.
 * Provides methods for user lookup and existence checks.
 */
package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by username (used for authentication)
     */
    Optional<User> findByUsername(String username);

    /**
     * Check if username already exists (for duplicate prevention)
     */
    boolean existsByUsername(String username);
}