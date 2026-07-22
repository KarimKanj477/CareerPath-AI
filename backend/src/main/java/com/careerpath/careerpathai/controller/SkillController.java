package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.dto.ApiResponse;
import com.careerpath.careerpathai.dto.SkillRequestDTO;
import com.careerpath.careerpathai.dto.SkillResponseDTO;
import com.careerpath.careerpathai.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SkillResponseDTO>>> getAllSkills() {

        List<SkillResponseDTO> skills = skillService.getAllSkills();

        ApiResponse<List<SkillResponseDTO>> response =
                new ApiResponse<>(true, "Skills retrieved successfully", skills);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillResponseDTO>> getSkillById(
            @PathVariable Integer id) {

        SkillResponseDTO skill = skillService.getSkillById(id);

        ApiResponse<SkillResponseDTO> response = new ApiResponse<>(true, "Skill retrieved successfully", skill);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SkillResponseDTO>> createSkill(
            @Valid @RequestBody SkillRequestDTO requestDTO) {

        SkillResponseDTO createdSkill = skillService.createSkill(requestDTO);

        ApiResponse<SkillResponseDTO> response = new ApiResponse<>(true, "Skill created successfully", createdSkill);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillResponseDTO>> updateSkill(@PathVariable Integer id,
                                                                     @Valid @RequestBody SkillRequestDTO requestDTO) {

        SkillResponseDTO updatedSkill =
                skillService.updateSkill(id, requestDTO);

        ApiResponse<SkillResponseDTO> response =
                new ApiResponse<>(true, "Skill updated successfully", updatedSkill
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteSkill(
            @PathVariable Integer id) {

        skillService.deleteSkill(id);

        ApiResponse<Object> response = new ApiResponse<>(true, "Skill deleted successfully", null);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SkillResponseDTO>>> searchSkills(
            @RequestParam String name) {

        List<SkillResponseDTO> skills =
                skillService.searchSkillsByName(name);

        ApiResponse<List<SkillResponseDTO>> response =
                new ApiResponse<>(true, "Skills found successfully", skills);

        return ResponseEntity.ok(response);
    }
}