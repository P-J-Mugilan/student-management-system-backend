package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.ApiResponse;
import com.example.studentmanagement.dto.BranchRequest;
import com.example.studentmanagement.dto.BranchResponse;
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

/**
 * Branch Controller
 *
 * Handles CRUD operations for academic branches.
 * Admin-only for create, update, delete operations.
 * Provides paginated and non-paginated endpoints.
 */
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
    @Operation(summary = "Create Branch", description = "Create a new academic branch (Admin only)")
    public ResponseEntity<ApiResponse<BranchResponse>> createBranch(
            @Valid @RequestBody BranchRequest request) {

        BranchResponse branchResponse = branchService.createBranch(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Branch created successfully", branchResponse));
    }

    @GetMapping
    @Operation(summary = "Get All Branches", description = "Retrieve list of all academic branches")
    public ResponseEntity<ApiResponse<List<BranchResponse>>> getAllBranches() {
        List<BranchResponse> branches = branchService.getAllBranches();
        return ResponseEntity.ok(ApiResponse.success("Branches retrieved successfully", branches));
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get Branches (Paginated)", description = "Retrieve paginated list of academic branches")
    public ResponseEntity<ApiResponse<Page<BranchResponse>>> getAllBranchesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<BranchResponse> branches = branchService.getAllBranches(pageable);
        return ResponseEntity.ok(ApiResponse.success("Branches retrieved successfully", branches));
    }

    @GetMapping("/{branchId}")
    @Operation(summary = "Get Branch by ID", description = "Retrieve specific branch details by branch ID")
    public ResponseEntity<ApiResponse<BranchResponse>> getBranchById(
            @PathVariable Long branchId) {

        BranchResponse branchResponse = branchService.getBranchById(branchId);
        return ResponseEntity.ok(ApiResponse.success("Branch retrieved successfully", branchResponse));
    }

    @PutMapping("/{branchId}")
    @Operation(summary = "Update Branch", description = "Update existing branch information (Admin only)")
    public ResponseEntity<ApiResponse<BranchResponse>> updateBranch(
            @PathVariable Long branchId,
            @Valid @RequestBody BranchRequest request) {

        BranchResponse branchResponse = branchService.updateBranch(branchId, request);
        return ResponseEntity.ok(ApiResponse.success("Branch updated successfully", branchResponse));
    }

    @DeleteMapping("/{branchId}")
    @Operation(summary = "Delete Branch", description = "Delete academic branch by ID (Admin only)")
    public ResponseEntity<ApiResponse<Void>> deleteBranch(@PathVariable Long branchId) {
        branchService.deleteBranch(branchId);
        return ResponseEntity.ok(ApiResponse.success("Branch deleted successfully"));
    }
}
