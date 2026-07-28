package com.careerpath.careerpathai.dto;

import java.time.LocalDateTime;

public class UserResponseDTO {

    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String experienceLevel;
    private String role;
    private LocalDateTime createdAt;

    public UserResponseDTO() {}

    public UserResponseDTO(Integer id, String firstname, String lastname,
                           String email, String experienceLevel,
                           String role, LocalDateTime createdAt) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.experienceLevel = experienceLevel;
        this.role = role;
        this.createdAt = createdAt;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
