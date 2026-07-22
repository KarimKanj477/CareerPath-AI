package com.careerpath.careerpathai.dto;

import java.time.LocalDateTime;

public class UserResponseDTO {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String experienceLevel;
    private LocalDateTime createdAt;
    private Integer roleId;
    private String roleName;


    public UserResponseDTO() {
    }

    public UserResponseDTO(Integer id, String firstname, String lastname, String email, String experienceLevel, LocalDateTime createdAt, Integer roleId, String roleName) {

        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.experienceLevel = experienceLevel;
        this.createdAt = createdAt;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
