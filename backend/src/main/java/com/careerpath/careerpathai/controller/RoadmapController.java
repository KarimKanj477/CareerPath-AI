package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.dto.ApiResponse;
import com.careerpath.careerpathai.dto.RoadmapRequestDTO;
import com.careerpath.careerpathai.dto.RoadmapResponseDTO;
import com.careerpath.careerpathai.dto.RoadmapStepRequestDTO;
import com.careerpath.careerpathai.dto.RoadmapStepResponseDTO;
import com.careerpath.careerpathai.entity.Roadmap;
import com.careerpath.careerpathai.entity.RoadmapStep;
import com.careerpath.careerpathai.entity.Skill;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.service.RoadmapService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roadmaps")
public class RoadmapController {

    private final RoadmapService roadmapService;

    public RoadmapController(RoadmapService roadmapService) {
        this.roadmapService = roadmapService;
    }

    private RoadmapResponseDTO toDTO(Roadmap r) {
        String careerTitle = r.getCareer() != null ? r.getCareer().getTitle() : null;
        return new RoadmapResponseDTO(r.getId(), r.getUser().getId(),
                r.getCareer().getId(), careerTitle, r.getStatus(), r.getCreatedAt());
    }

    private RoadmapStepResponseDTO toStepDTO(RoadmapStep s) {
        Integer skillId = s.getSkill() != null ? s.getSkill().getId() : null;
        String skillName = s.getSkill() != null ? s.getSkill().getName() : null;
        return new RoadmapStepResponseDTO(s.getId(), s.getRoadmap().getId(),
                skillId, skillName, s.getTitle(), s.getDescription(),
                s.getStepOrder(), s.getStatus());
    }

    // ── Roadmap CRUD ─────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoadmapResponseDTO>>> getMyRoadmaps(
            @AuthenticationPrincipal User principal) {
        List<RoadmapResponseDTO> list = roadmapService.getUserRoadmaps(principal.getId())
                .stream().map(this::toDTO).toList();
        return ResponseEntity.ok(new ApiResponse<>(true, "Roadmaps retrieved successfully", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoadmapResponseDTO>> getRoadmap(
            @PathVariable Integer id,
            @AuthenticationPrincipal User principal) {
        Roadmap roadmap = roadmapService.getRoadmapById(id, principal.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Roadmap retrieved successfully", toDTO(roadmap)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RoadmapResponseDTO>> createRoadmap(
            @Valid @RequestBody RoadmapRequestDTO dto,
            @AuthenticationPrincipal User principal) {
        Roadmap roadmap = roadmapService.createRoadmap(principal.getId(), dto.getCareerId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Roadmap created successfully", toDTO(roadmap)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<RoadmapResponseDTO>> updateStatus(
            @PathVariable Integer id,
            @RequestParam String status,
            @AuthenticationPrincipal User principal) {
        Roadmap roadmap = roadmapService.updateRoadmapStatus(id, status, principal.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Roadmap status updated", toDTO(roadmap)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteRoadmap(
            @PathVariable Integer id,
            @AuthenticationPrincipal User principal) {
        roadmapService.deleteRoadmap(id, principal.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Roadmap deleted successfully", null));
    }

    // ── Roadmap Steps ────────────────────────────────────────────────────────

    @GetMapping("/{roadmapId}/steps")
    public ResponseEntity<ApiResponse<List<RoadmapStepResponseDTO>>> getSteps(
            @PathVariable Integer roadmapId,
            @AuthenticationPrincipal User principal) {
        List<RoadmapStepResponseDTO> list = roadmapService.getRoadmapSteps(roadmapId, principal.getId())
                .stream().map(this::toStepDTO).toList();
        return ResponseEntity.ok(new ApiResponse<>(true, "Steps retrieved successfully", list));
    }

    @PostMapping("/{roadmapId}/steps")
    public ResponseEntity<ApiResponse<RoadmapStepResponseDTO>> addStep(
            @PathVariable Integer roadmapId,
            @Valid @RequestBody RoadmapStepRequestDTO dto,
            @AuthenticationPrincipal User principal) {

        RoadmapStep step = new RoadmapStep();
        step.setTitle(dto.getTitle());
        step.setDescription(dto.getDescription());
        step.setStepOrder(dto.getStepOrder());
        step.setStatus(dto.getStatus() != null ? dto.getStatus() : "NOT_STARTED");
        if (dto.getSkillId() != null) {
            Skill skill = new Skill();
            skill.setId(dto.getSkillId());
            step.setSkill(skill);
        }

        RoadmapStep saved = roadmapService.addRoadmapStep(roadmapId, step, principal.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Step added successfully", toStepDTO(saved)));
    }

    @PutMapping("/{roadmapId}/steps/{stepId}")
    public ResponseEntity<ApiResponse<RoadmapStepResponseDTO>> updateStep(
            @PathVariable Integer roadmapId,
            @PathVariable Integer stepId,
            @Valid @RequestBody RoadmapStepRequestDTO dto,
            @AuthenticationPrincipal User principal) {

        RoadmapStep updates = new RoadmapStep();
        updates.setTitle(dto.getTitle());
        updates.setDescription(dto.getDescription());
        updates.setStepOrder(dto.getStepOrder());
        updates.setStatus(dto.getStatus());
        if (dto.getSkillId() != null) {
            Skill skill = new Skill();
            skill.setId(dto.getSkillId());
            updates.setSkill(skill);
        }

        RoadmapStep updated = roadmapService.updateRoadmapStep(stepId, updates, principal.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Step updated successfully", toStepDTO(updated)));
    }

    @DeleteMapping("/{roadmapId}/steps/{stepId}")
    public ResponseEntity<ApiResponse<Object>> deleteStep(
            @PathVariable Integer roadmapId,
            @PathVariable Integer stepId,
            @AuthenticationPrincipal User principal) {
        roadmapService.deleteRoadmapStep(stepId, principal.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Step deleted successfully", null));
    }
}
