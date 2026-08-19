package com.careerpath.careerpathai.controller;

import com.careerpath.careerpathai.dto.ApiResponse;
import com.careerpath.careerpathai.entity.Career;
import com.careerpath.careerpathai.service.CareerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.careerpath.careerpathai.dto.CareerRequestDTO;
import com.careerpath.careerpathai.dto.CareerResponseDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/careers")
@SecurityRequirement(name = "bearerAuth")
public class CareerController {

    private final CareerService careerService;

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CareerResponseDTO>>> getAllCareers() {

        List<Career> careers = careerService.getAllCareers();

        List<CareerResponseDTO> responseDTOs = careers.stream()
                .map(career -> new CareerResponseDTO(
                        career.getId(),
                        career.getTitle(),
                        career.getDescription(),
                        career.getCategory(),
                        career.getAverageSalary(),
                        career.getDemandLevel()
                ))
                .toList();

        ApiResponse<List<CareerResponseDTO>> response = new ApiResponse<>(
                true,
                "Careers retrieved successfully",
                responseDTOs
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<CareerResponseDTO>> getCareerById(
            @PathVariable Integer id) {

        Career career = careerService.getCareerById(id);

        CareerResponseDTO responseDTO = new CareerResponseDTO(
                career.getId(),
                career.getTitle(),
                career.getDescription(),
                career.getCategory(),
                career.getAverageSalary(),
                career.getDemandLevel()
        );

        ApiResponse<CareerResponseDTO> response = new ApiResponse<>(true, "Career retrieved successfully",
                responseDTO);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<CareerResponseDTO>> createCareer(
            @Valid @RequestBody CareerRequestDTO requestDTO) {

        Career career = new Career();

        career.setTitle(requestDTO.getTitle());
        career.setDescription(requestDTO.getDescription());
        career.setCategory(requestDTO.getCategory());
        career.setAverageSalary(requestDTO.getAverageSalary());
        career.setDemandLevel(requestDTO.getDemandLevel());

        Career savedCareer = careerService.createCareer(career);

        CareerResponseDTO responseDTO = new CareerResponseDTO(
                savedCareer.getId(),
                savedCareer.getTitle(),
                savedCareer.getDescription(),
                savedCareer.getCategory(),
                savedCareer.getAverageSalary(),
                savedCareer.getDemandLevel()
        );

        ApiResponse<CareerResponseDTO> response = new ApiResponse<>(true, "Career created successfully",
                responseDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<Object>> deleteCareer(
            @PathVariable Integer id) {

        careerService.deleteCareer(id);

        ApiResponse<Object> response = new ApiResponse<>(true, "Career deleted successfully",
                null);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CareerResponseDTO>>> searchCareers(
            @RequestParam String title) {

        List<Career> careers = careerService.searchCareersByTitle(title);

        List<CareerResponseDTO> responseDTOs = careers.stream()
                .map(career -> new CareerResponseDTO(
                        career.getId(),
                        career.getTitle(),
                        career.getDescription(),
                        career.getCategory(),
                        career.getAverageSalary(),
                        career.getDemandLevel()
                ))
                .toList();

        ApiResponse<List<CareerResponseDTO>> response = new ApiResponse<>(true, "Careers found successfully",
                responseDTOs);

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CareerResponseDTO>> updateCareer(
            @PathVariable Integer id,
            @Valid @RequestBody CareerRequestDTO requestDTO) {

        Career career = new Career();

        career.setTitle(requestDTO.getTitle());
        career.setDescription(requestDTO.getDescription());
        career.setCategory(requestDTO.getCategory());
        career.setAverageSalary(requestDTO.getAverageSalary());
        career.setDemandLevel(requestDTO.getDemandLevel());

        Career updatedCareer = careerService.updateCareer(id, career);

        CareerResponseDTO responseDTO = new CareerResponseDTO(
                updatedCareer.getId(),
                updatedCareer.getTitle(),
                updatedCareer.getDescription(),
                updatedCareer.getCategory(),
                updatedCareer.getAverageSalary(),
                updatedCareer.getDemandLevel()
        );

        ApiResponse<CareerResponseDTO> response = new ApiResponse<>(
                true,
                "Career updated successfully",
                responseDTO
        );

        return ResponseEntity.ok(response);
    }
}