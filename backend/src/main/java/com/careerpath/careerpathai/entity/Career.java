package com.careerpath.careerpathai.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "careers")
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "category", length = 100)
    private String category;

    // FIX: was "average_salary" (snake_case) — the actual DB column is
    // "averageSalary" (camelCase, no underscore). With ddl-auto=none,
    // Hibernate does not create/rename columns, so the mismatched name
    // caused an "Unknown column 'average_salary'" SQL error on every
    // Career read/write.
    @Column(name = "averageSalary")
    private Double averageSalary;

    // FIX: same issue — was "demand_level", DB column is "demandLevel".
    @Column(name = "demandLevel", length = 50)
    private String demandLevel;


    public Career() {
    }

    public Career(Integer id, String title, String description,
                  String category, Double averageSalary, String demandLevel) {
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
