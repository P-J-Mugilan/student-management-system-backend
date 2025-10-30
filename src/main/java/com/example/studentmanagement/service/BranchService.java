/**
 * Branch Service Interface

 * Manages academic branch operations including CRUD and pagination.
 * Handles branch creation, retrieval, updates, and deletion.
 */
package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.BranchRequest;
import com.example.studentmanagement.entity.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BranchService {

    /**
     * Creates a new academic branch with validated data
     */
    Branch createBranch(BranchRequest request);

    /**
     * Retrieves all branches in the system
     */
    List<Branch> getAllBranches();

    /**
     * Retrieves branches with pagination support
     */
    Page<Branch> getAllBranches(Pageable pageable);

    /**
     * Finds a specific branch by its unique identifier
     */
    Branch getBranchById(Long branchId);

    /**
     * Updates an existing branch with new information
     */
    Branch updateBranch(Long branchId, BranchRequest request);

    /**
     * Deletes a branch and handles associated relationships

     * Contract:
     * - Prevents deletion if branch has students or professors assigned
     * - Cascades deletion appropriately based on entity relationships
     * - Returns error if branch is in use by other entities
     */
    void deleteBranch(Long branchId);
}