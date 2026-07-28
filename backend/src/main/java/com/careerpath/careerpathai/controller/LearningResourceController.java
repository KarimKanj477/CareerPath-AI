package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.dto.ApiResponse;
import com.careerpath.careerpathai.dto.LearningResourceRequestDTO;
import com.careerpath.careerpathai.dto.LearningResourceResponseDTO;
import com.careerpath.careerpathai.entity.LearningResource;
import com.careerpath.careerpathai.entity.Skill;
import com.careerpath.careerpathai.service.LearningResourceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learning-resources")
public class LearningResourceController {

    private final LearningResourceService learningResourceService;

    public LearningResourceController(LearningResourceService learningResourceService) {
        this.learningResourceService = learningResourceService;
    }

    private LearningResourceResponseDTO toDTO(LearningResource r) {
        String skillName = r.getSkill() != null ? r.getSkill().getName() : null;
        Integer skillId = r.getSkill() != null ? r.getSkill().getId() : null;
        return new LearningResourceResponseDTO(r.getId(), skillId, skillName,
                r.getTitle(), r.getUrl(), r.getType(), r.getProvider(), r.getIsFree());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LearningResourceResponseDTO>>> getAllResources(
            @RequestParam(required = false) Integer skillId,
            @RequestParam(required = false) Boolean free) {

        List<LearningResourceResponseDTO> list;
        if (skillId != null && free != null) {
            list = learningResourceService.getResourcesBySkillAndFree(skillId, free)
                    .stream().map(this::toDTO).toList();
        } else if (skillId != null) {
            list = learningResourceService.getResourcesBySkill(skillId)
                    .stream().map(this::toDTO).toList();
        } else {
            list = learningResourceService.getAllResources()
                    .stream().map(this::toDTO).toList();
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Resources retrieved successfully", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LearningResourceResponseDTO>> getResource(
            @PathVariable Integer id) {
        LearningResource resource = learningResourceService.getResourceById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Resource retrieved successfully", toDTO(resource)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LearningResourceResponseDTO>> createResource(
            @Valid @RequestBody LearningResourceRequestDTO dto) {

        LearningResource resource = new LearningResource();
        resource.setTitle(dto.getTitle());
        resource.setUrl(dto.getUrl());
        resource.setType(dto.getType());
        resource.setProvider(dto.getProvider());
        resource.setIsFree(dto.getIsFree() != null ? dto.getIsFree() : true);
        Skill skill = new Skill();
        skill.setId(dto.getSkillId());
        resource.setSkill(skill);

        LearningResource saved = learningResourceService.createResource(resource);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Resource created successfully", toDTO(saved)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LearningResourceResponseDTO>> updateResource(
            @PathVariable Integer id,
            @Valid @RequestBody LearningResourceRequestDTO dto) {

        LearningResource updates = new LearningResource();
        updates.setTitle(dto.getTitle());
        updates.setUrl(dto.getUrl());
        updates.setType(dto.getType());
        updates.setProvider(dto.getProvider());
        updates.setIsFree(dto.getIsFree());
        if (dto.getSkillId() != null) {
            Skill skill = new Skill();
            skill.setId(dto.getSkillId());
            updates.setSkill(skill);
        }

        LearningResource updated = learningResourceService.updateResource(id, updates);
        return ResponseEntity.ok(new ApiResponse<>(true, "Resource updated successfully", toDTO(updated)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteResource(@PathVariable Integer id) {
        learningResourceService.deleteResource(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Resource deleted successfully", null));
    }
}
