package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.dto.ApiResponse;
import com.careerpath.careerpathai.dto.UserResponseDTO;
import com.careerpath.careerpathai.dto.UserUpdateDTO;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.exception.UnauthorizedAccessException;
import com.careerpath.careerpathai.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private UserResponseDTO toDTO(User user) {
        String role = user.getRole() != null ? user.getRole().getName() : null;
        return new UserResponseDTO(user.getId(), user.getFirstname(), user.getLastname(),
                user.getEmail(), user.getExperienceLevel(), role, user.getCreatedAt());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers().stream()
                .map(this::toDTO).toList();
        return ResponseEntity.ok(new ApiResponse<>(true, "Users retrieved successfully", users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(
            @PathVariable Integer id,
            @AuthenticationPrincipal User principal) {

        if (!principal.getId().equals(id) && !isAdmin(principal)) {
            throw new UnauthorizedAccessException("You are not authorized to view this user.");
        }

        User user = userService.getUserById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "User retrieved successfully", toDTO(user)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody UserUpdateDTO dto,
            @AuthenticationPrincipal User principal) {

        if (!principal.getId().equals(id) && !isAdmin(principal)) {
            throw new UnauthorizedAccessException("You are not authorized to update this user.");
        }

        User updates = new User();
        updates.setFirstname(dto.getFirstname());
        updates.setLastname(dto.getLastname());
        updates.setExperienceLevel(dto.getExperienceLevel());

        User updated = userService.updateUser(id, updates);
        return ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully", toDTO(updated)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(
            @PathVariable Integer id,
            @AuthenticationPrincipal User principal) {

        if (!principal.getId().equals(id) && !isAdmin(principal)) {
            throw new UnauthorizedAccessException("You are not authorized to delete this user.");
        }

        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", null));
    }

    private boolean isAdmin(User user) {
        return user.getRole() != null && user.getRole().getName().equalsIgnoreCase("ADMIN");
    }
}
