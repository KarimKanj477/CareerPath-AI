package com.careerpath.careerpathai.dto;

import java.util.List;

public class CareerRecommendationResponseDTO {

    private Integer careerId;
    private String careerTitle;
    private String careerDescription;
    private String careerCategory;
    private Double averageSalary;
    private String demandLevel;

    private double matchPercentage;
    private int totalRequiredSkills;

    private List<RecommendationSkillDTO> matchedSkills;
    private List<RecommendationSkillDTO> missingSkills;

    public CareerRecommendationResponseDTO() {
    }

    public CareerRecommendationResponseDTO(
            Integer careerId,
            String careerTitle,
            String careerDescription,
            String careerCategory,
            Double averageSalary,
            String demandLevel,
            double matchPercentage,
            int totalRequiredSkills,
            List<RecommendationSkillDTO> matchedSkills,
            List<RecommendationSkillDTO> missingSkills
    ) {
        this.careerId = careerId;
        this.careerTitle = careerTitle;
        this.careerDescription = careerDescription;
        this.careerCategory = careerCategory;
        this.averageSalary = averageSalary;
        this.demandLevel = demandLevel;
        this.matchPercentage = matchPercentage;
        this.totalRequiredSkills = totalRequiredSkills;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
    }

    public Integer getCareerId() {
        return careerId;
    }

    public void setCareerId(Integer careerId) {
        this.careerId = careerId;
    }

    public String getCareerTitle() {
        return careerTitle;
    }

    public void setCareerTitle(String careerTitle) {
        this.careerTitle = careerTitle;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public String getCareerCategory() {
        return careerCategory;
    }

    public void setCareerCategory(String careerCategory) {
        this.careerCategory = careerCategory;
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

    public double getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(double matchPercentage) {
        this.matchPercentage = matchPercentage;
    }

    public int getTotalRequiredSkills() {
        return totalRequiredSkills;
    }

    public void setTotalRequiredSkills(int totalRequiredSkills) {
        this.totalRequiredSkills = totalRequiredSkills;
    }

    public List<RecommendationSkillDTO> getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(
            List<RecommendationSkillDTO> matchedSkills
    ) {
        this.matchedSkills = matchedSkills;
    }

    public List<RecommendationSkillDTO> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(
            List<RecommendationSkillDTO> missingSkills
    ) {
        this.missingSkills = missingSkills;
    }
}