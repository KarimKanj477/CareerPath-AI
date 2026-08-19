package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.entity.Role;
import com.careerpath.careerpathai.service.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RestController //handles HTTP requests
@RequestMapping("/api/roles")
@SecurityRequirement(name = "bearerAuth")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) { //Dependency Injection
        this.roleService = roleService;
    }

    @GetMapping
    public Page<Role> getAllRoles(
            @RequestParam(defaultValue = "0")int page ,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return roleService.getAllRoles(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> getRoleById(
            @PathVariable Integer id) {

        Role role = roleService.getRoleById(id);

        RoleResponseDTO responseDTO = new RoleResponseDTO(
                role.getId(),
                role.getName(),
                role.getDescription()
        );

        ApiResponse<RoleResponseDTO> response = new ApiResponse<>(
                true,
                "Role retrieved successfully",
                responseDTO
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

        RoleResponseDTO responseDTO = new RoleResponseDTO(
                savedRole.getId(),
                savedRole.getName(),
                savedRole.getDescription()
        );

        ApiResponse<RoleResponseDTO> response = new ApiResponse<>(
                true,
                "Role created successfully",
                responseDTO
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Integer id,
                           @Valid @RequestBody Role role) {

        role.setId(id);
        return roleService.saveRole(role);
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
    public List<Role> searchRoles(
            @RequestParam String name
    )
    {

        return roleService.searchRolesByName(name);

    }
}