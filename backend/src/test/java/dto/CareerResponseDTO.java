package com.careerpath.careerpathai.dto;

public class CareerResponseDTO {

    private Integer id;
    private String title;
    private String description;
    private String category;
    private Double averageSalary;
    private String demandLevel;

    public CareerResponseDTO() {
    }

    public CareerResponseDTO(
            Integer id,
            String title,
            String description,
            String category,
            Double averageSalary,
            String demandLevel) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.averageSalary = averageSalary;
        this.demandLevel = demandLevel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(Double averageSalary) {
        this.averageSalary = averageSalary;
    }

    public String getDemandLevel() {
        return demandLevel;
    }

    public void setDemandLevel(String demandLevel) {
        this.demandLevel = demandLevel;
    }
}