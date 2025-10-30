/**
 * Branch Controller
 *
 * Handles academic branch management operations.
 * Admin-only access for create, update, delete operations.
 */
package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.ApiResponse;
import com.example.studentmanagement.dto.BranchRequest;
import com.example.studentmanagement.entity.Branch;
import com.example.studentmanagement.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@Tag(name = "2. Branch Management", description = "Academic branch management endpoints (Admin access only)")
@CrossOrigin(origins = "*")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping
    @Operation(summary = "Create Branch", description = "Create a new academic branch - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<Branch>> createBranch(
            @Valid @RequestBody BranchRequest request) {
        Branch branch = branchService.createBranch(request);
        return new ResponseEntity<>(ApiResponse.created("Branch created successfully", branch), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get All Branches", description = "Retrieve list of all academic branches")
    public ResponseEntity<ApiResponse<List<Branch>>> getAllBranches() {
        List<Branch> branches = branchService.getAllBranches();
        return ResponseEntity.ok(ApiResponse.success("Branches retrieved successfully", branches));
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get Branches (Paginated)", description = "Retrieve paginated list of academic branches with page and size parameters")
    public ResponseEntity<ApiResponse<Page<Branch>>> getAllBranchesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Branch> branches = branchService.getAllBranches(pageable);
        return ResponseEntity.ok(ApiResponse.success("Branches retrieved successfully", branches));
    }

    @GetMapping("/{branchId}")
    @Operation(summary = "Get Branch by ID", description = "Retrieve specific branch details by branch ID")
    public ResponseEntity<ApiResponse<Branch>> getBranchById(
            @PathVariable Long branchId) {
        Branch branch = branchService.getBranchById(branchId);
        return ResponseEntity.ok(ApiResponse.success("Branch retrieved successfully", branch));
    }

    @PutMapping("/{branchId}")
    @Operation(summary = "Update Branch", description = "Update existing branch information - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<Branch>> updateBranch(
            @PathVariable Long branchId,
            @Valid @RequestBody BranchRequest request) {
        Branch branch = branchService.updateBranch(branchId, request);
        return ResponseEntity.ok(ApiResponse.success("Branch updated successfully", branch));
    }

    @DeleteMapping("/{branchId}")
    @Operation(summary = "Delete Branch", description = "Delete academic branch by ID - ADMIN ACCESS REQUIRED")
    public ResponseEntity<ApiResponse<Void>> deleteBranch(
            @PathVariable Long branchId) {
        branchService.deleteBranch(branchId);
        return ResponseEntity.ok(ApiResponse.success("Branch deleted successfully"));
    }
}