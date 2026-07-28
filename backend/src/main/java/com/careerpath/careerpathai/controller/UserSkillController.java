package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.dto.ApiResponse;
import com.careerpath.careerpathai.dto.UserSkillRequestDTO;
import com.careerpath.careerpathai.dto.UserSkillResponseDTO;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.entity.UserSkill;
import com.careerpath.careerpathai.service.UserSkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/skills")
public class UserSkillController {

    private final UserSkillService userSkillService;

    public UserSkillController(UserSkillService userSkillService) {
        this.userSkillService = userSkillService;
    }

    private UserSkillResponseDTO toDTO(UserSkill us) {
        String skillName = us.getSkill() != null ? us.getSkill().getName() : null;
        String skillCategory = us.getSkill() != null ? us.getSkill().getCategory() : null;
        return new UserSkillResponseDTO(us.getId(), us.getUser().getId(),
                us.getSkill().getId(), skillName, skillCategory, us.getProficiencyLevel());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserSkillResponseDTO>>> getUserSkills(
            @PathVariable Integer userId) {
        List<UserSkillResponseDTO> list = userSkillService.getUserSkills(userId)
                .stream().map(this::toDTO).toList();
        return ResponseEntity.ok(new ApiResponse<>(true, "Skills retrieved successfully", list));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserSkillResponseDTO>> addUserSkill(
            @PathVariable Integer userId,
            @Valid @RequestBody UserSkillRequestDTO dto,
            @AuthenticationPrincipal User principal) {

        UserSkill saved = userSkillService.addUserSkill(
                principal.getId(), dto.getSkillId(), dto.getProficiencyLevel());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Skill added successfully", toDTO(saved)));
    }

    @PutMapping("/{userSkillId}")
    public ResponseEntity<ApiResponse<UserSkillResponseDTO>> updateUserSkill(
            @PathVariable Integer userId,
            @PathVariable Integer userSkillId,
            @RequestBody UserSkillRequestDTO dto,
            @AuthenticationPrincipal User principal) {

        UserSkill updated = userSkillService.updateUserSkill(
                userSkillId, dto.getProficiencyLevel(), principal.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Skill updated successfully", toDTO(updated)));
    }

    @DeleteMapping("/{userSkillId}")
    public ResponseEntity<ApiResponse<Object>> removeUserSkill(
            @PathVariable Integer userId,
            @PathVariable Integer userSkillId,
            @AuthenticationPrincipal User principal) {

        userSkillService.removeUserSkill(userSkillId, principal.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Skill removed successfully", null));
    }
}
