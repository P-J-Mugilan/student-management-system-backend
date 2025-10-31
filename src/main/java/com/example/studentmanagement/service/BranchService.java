package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.BranchRequest;
import com.example.studentmanagement.dto.BranchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing academic branches.
 * Provides CRUD operations with role-based access.
 */
public interface BranchService {

    /** Create a new branch (ADMIN only) */
    BranchResponse createBranch(BranchRequest request);

    /** Retrieve all branches (ADMIN sees all, PROFESSOR sees own) */
    List<BranchResponse> getAllBranches();

    /** Retrieve paginated branches (ADMIN only) */
    Page<BranchResponse> getAllBranches(Pageable pageable);

    /** Get a specific branch by ID (ADMIN sees all, PROFESSOR sees own) */
    BranchResponse getBranchById(Long branchId);

    /** Update an existing branch (ADMIN only) */
    BranchResponse updateBranch(Long branchId, BranchRequest request);

    /** Delete a branch (ADMIN only, cannot delete if associated entities exist) */
    void deleteBranch(Long branchId);
}
