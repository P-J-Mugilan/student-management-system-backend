package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.dto.BranchRequest;
import com.example.studentmanagement.dto.BranchResponse;
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
import java.util.stream.Collectors;

@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final UserService userService;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, UserService userService) {
        this.branchRepository = branchRepository;
        this.userService = userService;
    }

    private BranchResponse mapToResponse(Branch branch) {
        return new BranchResponse(branch);
    }

    @Override
    public BranchResponse createBranch(BranchRequest request) {
        User currentUser = userService.getCurrentUserEntity(); // entity, not DTO

        if (!currentUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("Only admin can create branches");
        }

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new BadRequestException("Branch name is required");
        }

        if (branchRepository.existsByName(request.getName().trim())) {
            throw new ConflictException("Branch with name " + request.getName() + " already exists");
        }

        Branch branch = new Branch(request.getName().trim(), request.getDescription() != null ? request.getDescription().trim() : null);
        return mapToResponse(branchRepository.save(branch));
    }

    @Override
    public List<BranchResponse> getAllBranches() {
        User currentUser = userService.getCurrentUserEntity();

        if (currentUser.getRole().equals(Role.PROFESSOR)) {
            return List.of(mapToResponse(currentUser.getBranch()));
        }

        return branchRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BranchResponse> getAllBranches(Pageable pageable) {
        User currentUser = userService.getCurrentUserEntity();

        if (currentUser.getRole().equals(Role.PROFESSOR)) {
            return Page.empty(); // professors see only their branch in list
        }

        return branchRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public BranchResponse getBranchById(Long branchId) {
        User currentUser = userService.getCurrentUserEntity();

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", branchId));

        if (currentUser.getRole().equals(Role.PROFESSOR) &&
                !currentUser.getBranch().getBranchId().equals(branchId)) {
            throw new ForbiddenException("You can only access your own branch");
        }

        return mapToResponse(branch);
    }

    @Override
    public BranchResponse updateBranch(Long branchId, BranchRequest request) {
        User currentUser = userService.getCurrentUserEntity();

        if (!currentUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("Only admin can update branches");
        }

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", branchId));

        if (request.getName() != null && !request.getName().equals(branch.getName())) {
            if (branchRepository.existsByName(request.getName().trim())) {
                throw new ConflictException("Branch with name " + request.getName() + " already exists");
            }
            branch.setName(request.getName().trim());
        }

        if (request.getDescription() != null) {
            branch.setDescription(request.getDescription().trim());
        }

        return mapToResponse(branchRepository.save(branch));
    }

    @Override
    public void deleteBranch(Long branchId) {
        User currentUser = userService.getCurrentUserEntity();

        if (!currentUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("Only admin can delete branches");
        }

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", branchId));

        if (!branch.getStudents().isEmpty() || !branch.getProfessors().isEmpty()) {
            throw new BadRequestException("Cannot delete branch with associated students or professors");
        }

        branchRepository.delete(branch);
    }
}
