package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.dto.BranchRequest;
import com.example.studentmanagement.entity.Branch;
import com.example.studentmanagement.entity.Role;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.exception.BadRequestException;
import com.example.studentmanagement.exception.ConflictException;
import com.example.studentmanagement.exception.ForbiddenException;
import com.example.studentmanagement.exception.ResourceNotFoundException;
import com.example.studentmanagement.repository.BranchRepository;
import com.example.studentmanagement.service.BranchService;
import com.example.studentmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final UserService userService;

    // Constructor-based dependency injection
    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, UserService userService) {
        this.branchRepository = branchRepository;
        this.userService = userService;
    }

    @Override
    public Branch createBranch(BranchRequest request) {
        User currentUser = userService.getCurrentUser();

        if (!currentUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("Only admin can create branches");
        }

        // Validate branch name
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new BadRequestException("Branch name is required");
        }

        // Check if branch name already exists
        if (branchRepository.existsByName(request.getName())) {
            throw new ConflictException("Branch with name " + request.getName() + " already exists");
        }

        Branch branch = new Branch();
        branch.setName(request.getName());
        branch.setDescription(request.getDescription());

        return branchRepository.save(branch);
    }

    @Override
    public List<Branch> getAllBranches() {
        User currentUser = userService.getCurrentUser();

        if (currentUser.getRole().equals(Role.PROFESSOR)) {
            // Professor can only see their own branch
            return List.of(currentUser.getBranch());
        }

        return branchRepository.findAll();
    }

    @Override
    public Page<Branch> getAllBranches(Pageable pageable) {
        User currentUser = userService.getCurrentUser();

        if (currentUser.getRole().equals(Role.PROFESSOR)) {
            return Page.empty();
        }

        return branchRepository.findAll(pageable);
    }

    @Override
    public Branch getBranchById(Long branchId) {
        User currentUser = userService.getCurrentUser();

        // Professors can only access their own branch
        if (currentUser.getRole().equals(Role.PROFESSOR) &&
                !currentUser.getBranch().getBranchId().equals(branchId)) {
            throw new ForbiddenException("You can only access your own branch");
        }

        return branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", branchId));
    }

    @Override
    public Branch updateBranch(Long branchId, BranchRequest request) {
        User currentUser = userService.getCurrentUser();

        if (!currentUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("Only admin can update branches");
        }

        Branch branch = getBranchById(branchId);

        if (request.getName() != null) {
            // Check if new name already exists (excluding current branch)
            if (!branch.getName().equals(request.getName()) &&
                    branchRepository.existsByName(request.getName())) {
                throw new ConflictException("Branch with name " + request.getName() + " already exists");
            }
            branch.setName(request.getName());
        }
        if (request.getDescription() != null) {
            branch.setDescription(request.getDescription());
        }

        return branchRepository.save(branch);
    }

    @Override
    public void deleteBranch(Long branchId) {
        User currentUser = userService.getCurrentUser();

        if (!currentUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("Only admin can delete branches");
        }

        Branch branch = getBranchById(branchId);

        if (!branch.getStudents().isEmpty() || !branch.getProfessors().isEmpty()) {
            throw new BadRequestException("Cannot delete branch with associated students or professors");
        }

        branchRepository.delete(branch);
    }
}