package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.dto.ApiResponse;
import com.careerpath.careerpathai.dto.CertificationRequestDTO;
import com.careerpath.careerpathai.dto.CertificationResponseDTO;
import com.careerpath.careerpathai.entity.Certification;
import com.careerpath.careerpathai.entity.User;
import com.careerpath.careerpathai.exception.UnauthorizedAccessException;
import com.careerpath.careerpathai.service.CertificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/certifications")
public class CertificationController {

    private final CertificationService certificationService;

    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    private CertificationResponseDTO toDTO(Certification c) {
        return new CertificationResponseDTO(c.getId(), c.getUser().getId(), c.getName(),
                c.getIssuer(), c.getIssueDate(), c.getExpiryDate(),
                c.getCredentialUrl(), c.getCreatedAt());
    }

    private void checkReadAccess(Integer userId, User principal) {
        boolean isAdmin = principal.getRole() != null
                && principal.getRole().getName().equalsIgnoreCase("ADMIN");
        if (!principal.getId().equals(userId) && !isAdmin) {
            throw new UnauthorizedAccessException(
                    "You are not authorized to view this user's certifications.");
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CertificationResponseDTO>>> getCertifications(
            @PathVariable Integer userId,
            @AuthenticationPrincipal User principal) {
        checkReadAccess(userId, principal);
        List<CertificationResponseDTO> list = certificationService.getCertificationsByUserId(userId)
                .stream().map(this::toDTO).toList();
        return ResponseEntity.ok(new ApiResponse<>(true, "Certifications retrieved successfully", list));
    }

    @GetMapping("/{certId}")
    public ResponseEntity<ApiResponse<CertificationResponseDTO>> getCertification(
            @PathVariable Integer userId,
            @PathVariable Integer certId,
            @AuthenticationPrincipal User principal) {
        checkReadAccess(userId, principal);
        Certification c = certificationService.getCertificationById(certId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Certification retrieved successfully", toDTO(c)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CertificationResponseDTO>> createCertification(
            @PathVariable Integer userId,
            @Valid @RequestBody CertificationRequestDTO dto,
            @AuthenticationPrincipal User principal) {

        Certification certification = new Certification();
        certification.setName(dto.getName());
        certification.setIssuer(dto.getIssuer());
        certification.setIssueDate(dto.getIssueDate());
        certification.setExpiryDate(dto.getExpiryDate());
        certification.setCredentialUrl(dto.getCredentialUrl());

        Certification saved = certificationService.createCertification(principal.getId(), certification);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Certification created successfully", toDTO(saved)));
    }

    @PutMapping("/{certId}")
    public ResponseEntity<ApiResponse<CertificationResponseDTO>> updateCertification(
            @PathVariable Integer userId,
            @PathVariable Integer certId,
            @Valid @RequestBody CertificationRequestDTO dto,
            @AuthenticationPrincipal User principal) {

        Certification updates = new Certification();
        updates.setName(dto.getName());
        updates.setIssuer(dto.getIssuer());
        updates.setIssueDate(dto.getIssueDate());
        updates.setExpiryDate(dto.getExpiryDate());
        updates.setCredentialUrl(dto.getCredentialUrl());

        Certification updated = certificationService.updateCertification(certId, updates, principal.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Certification updated successfully", toDTO(updated)));
    }

    @DeleteMapping("/{certId}")
    public ResponseEntity<ApiResponse<Object>> deleteCertification(
            @PathVariable Integer userId,
            @PathVariable Integer certId,
            @AuthenticationPrincipal User principal) {

        certificationService.deleteCertification(certId, principal.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, "Certification deleted successfully", null));
    }
}
