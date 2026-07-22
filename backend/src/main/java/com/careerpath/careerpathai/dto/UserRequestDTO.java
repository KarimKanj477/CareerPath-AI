package com.careerpath.careerpathai.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



public class UserRequestDTO {

    @NotBlank(message = "FirstName is required")
    @Size(max=100, message = "firstname can not exceed 100 characters.")
    private String firstname;

    @NotBlank(message = "LastName is required")
    @Size(max = 100, message = "lastname can not exceed 100 characters.")
    private String lastname;


    @NotBlank(message = "Email is required")
    @Size(max = 150, message = "Email format is required.")
    private String email;


    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 255, message = "Password must contain at least 8 characters.")
    private String password;


    @Size(max = 50, message = "Experience level cannot exceed 50 characters.")
    private String experienceLevel;



    @NotNull(message = "Role id is required.")
    private Integer roleId;

    public UserRequestDTO() {
    }

    public UserRequestDTO(String firstname, String lastname, String email, String password, String experienceLevel, Integer roleId) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.experienceLevel = experienceLevel;
        this.roleId = roleId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }


}
