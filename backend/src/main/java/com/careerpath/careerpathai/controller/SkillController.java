package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.dto.ApiResponse;
import com.careerpath.careerpathai.dto.SkillRequestDTO;
import com.careerpath.careerpathai.dto.SkillResponseDTO;
import com.careerpath.careerpathai.entity.Skill;
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

    private SkillResponseDTO toResponseDTO(Skill skill) {
        return new SkillResponseDTO(
                skill.getId(),
                skill.getName(),
                skill.getDescription(),
                skill.getCategory()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SkillResponseDTO>>> getAllSkills() {

        List<SkillResponseDTO> responseDTOs = skillService.getAllSkills().stream()
                .map(this::toResponseDTO)
                .toList();

        ApiResponse<List<SkillResponseDTO>> response = new ApiResponse<>(
                true,
                "Skills retrieved successfully",
                responseDTOs
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillResponseDTO>> getSkillById(
            @PathVariable Integer id) {

        Skill skill = skillService.getSkillById(id);

        ApiResponse<SkillResponseDTO> response = new ApiResponse<>(
                true,
                "Skill retrieved successfully",
                toResponseDTO(skill)
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SkillResponseDTO>> createSkill(
            @Valid @RequestBody SkillRequestDTO requestDTO) {

        Skill skill = new Skill();
        skill.setName(requestDTO.getName());
        skill.setDescription(requestDTO.getDescription());
        skill.setCategory(requestDTO.getCategory());

        Skill savedSkill = skillService.createSkill(skill);

        ApiResponse<SkillResponseDTO> response = new ApiResponse<>(
                true,
                "Skill created successfully",
                toResponseDTO(savedSkill)
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillResponseDTO>> updateSkill(
            @PathVariable Integer id,
            @Valid @RequestBody SkillRequestDTO requestDTO) {

        Skill skill = new Skill();
        skill.setName(requestDTO.getName());
        skill.setDescription(requestDTO.getDescription());
        skill.setCategory(requestDTO.getCategory());

        Skill updatedSkill = skillService.updateSkill(id, skill);

        ApiResponse<SkillResponseDTO> response = new ApiResponse<>(
                true,
                "Skill updated successfully",
                toResponseDTO(updatedSkill)
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteSkill(@PathVariable Integer id) {

        skillService.deleteSkill(id);

        ApiResponse<Object> response = new ApiResponse<>(
                true,
                "Skill deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SkillResponseDTO>>> searchSkills(
            @RequestParam String name) {

        List<SkillResponseDTO> responseDTOs = skillService.searchSkillsByName(name).stream()
                .map(this::toResponseDTO)
                .toList();

        ApiResponse<List<SkillResponseDTO>> response = new ApiResponse<>(
                true,
                "Skills found successfully",
                responseDTOs
        );

        return ResponseEntity.ok(response);
    }
}
