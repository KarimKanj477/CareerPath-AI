package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.entity.Role;
import com.careerpath.careerpathai.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;
import com.careerpath.careerpathai.dto.RoleRequestDTO;
import com.careerpath.careerpathai.dto.RoleResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.careerpath.careerpathai.dto.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController // handles HTTP requests
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) { // Dependency Injection
        this.roleService = roleService;
    }

    private RoleResponseDTO toResponseDTO(Role role) {
        return new RoleResponseDTO(role.getId(), role.getName(), role.getDescription());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<RoleResponseDTO>>> getAllRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<RoleResponseDTO> rolePage = roleService.getAllRoles(pageable)
                .map(this::toResponseDTO);

        ApiResponse<Page<RoleResponseDTO>> response = new ApiResponse<>(
                true,
                "Roles retrieved successfully",
                rolePage
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> getRoleById(
            @PathVariable Integer id) {

        Role role = roleService.getRoleById(id);

        ApiResponse<RoleResponseDTO> response = new ApiResponse<>(
                true,
                "Role retrieved successfully",
                toResponseDTO(role)
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponseDTO>> createRole(
            @Valid @RequestBody RoleRequestDTO roleRequestDTO) {

        Role role = new Role();
        role.setName(roleRequestDTO.getName());
        role.setDescription(roleRequestDTO.getDescription());

        Role savedRole = roleService.saveRole(role);

        ApiResponse<RoleResponseDTO> response = new ApiResponse<>(
                true,
                "Role created successfully",
                toResponseDTO(savedRole)
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> updateRole(
            @PathVariable Integer id,
            @Valid @RequestBody RoleRequestDTO roleRequestDTO) {

        Role role = new Role();
        role.setName(roleRequestDTO.getName());
        role.setDescription(roleRequestDTO.getDescription());

        Role updatedRole = roleService.updateRole(id, role);

        ApiResponse<RoleResponseDTO> response = new ApiResponse<>(
                true,
                "Role updated successfully",
                toResponseDTO(updatedRole)
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteRole(@PathVariable Integer id) {

        roleService.deleteRole(id);

        ApiResponse<Object> response = new ApiResponse<>(
                true,
                "Role deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<RoleResponseDTO>>> searchRoles(
            @RequestParam String name) {

        List<RoleResponseDTO> responseDTOs = roleService.searchRolesByName(name).stream()
                .map(this::toResponseDTO)
                .toList();

        ApiResponse<List<RoleResponseDTO>> response = new ApiResponse<>(
                true,
                "Roles found successfully",
                responseDTOs
        );

        return ResponseEntity.ok(response);
    }
}
