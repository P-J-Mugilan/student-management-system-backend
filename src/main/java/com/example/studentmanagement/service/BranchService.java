package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.BranchRequest;
import com.example.studentmanagement.entity.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BranchService {
    Branch createBranch(BranchRequest request);
    List<Branch> getAllBranches();
    Page<Branch> getAllBranches(Pageable pageable);
    Branch getBranchById(Long branchId);
    Branch updateBranch(Long branchId, BranchRequest request);
    void deleteBranch(Long branchId);
}