package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.dto.ApiResponse;
import com.careerpath.careerpathai.dto.UserSkillRequestDTO;
import com.careerpath.careerpathai.dto.UserSkillResponseDTO;
import com.careerpath.careerpathai.service.UserSkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/user-skills")
@SecurityRequirement(name = "bearerAuth")
public class UserSkillController {

    private final UserSkillService userSkillService;

    public UserSkillController(UserSkillService userSkillService) {
        this.userSkillService = userSkillService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<UserSkillResponseDTO>>> getMySkills(Authentication authentication) {

        List<UserSkillResponseDTO> userSkills = userSkillService.getMySkills(authentication.getName());

        ApiResponse<List<UserSkillResponseDTO>> response =
                new ApiResponse<>(true, "User skills retrieved successfully.", userSkills);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserSkillResponseDTO>> addSkill(Authentication authentication,
            @Valid @RequestBody UserSkillRequestDTO request
    ) {

        UserSkillResponseDTO userSkill =
                userSkillService.addSkill(authentication.getName(), request);

        ApiResponse<UserSkillResponseDTO> response =
                new ApiResponse<>(true, "Skill added to your profile successfully.", userSkill);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserSkillResponseDTO>> updateSkill(@PathVariable Integer id, Authentication authentication,
            @Valid @RequestBody UserSkillRequestDTO request
    ) {

        UserSkillResponseDTO userSkill =
                userSkillService.updateSkill(id, authentication.getName(), request);

        ApiResponse<UserSkillResponseDTO> response =
                new ApiResponse<>(true, "User skill updated successfully.", userSkill);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteSkill(@PathVariable Integer id, Authentication authentication) {

        userSkillService.deleteSkill(id, authentication.getName());

        ApiResponse<Object> response =
                new ApiResponse<>(true, "User skill deleted successfully.", null);

        return ResponseEntity.ok(response);
    }
}