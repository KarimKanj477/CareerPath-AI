package com.careerpath.careerpathai.dto;

import jakarta.validation.constraints.NotNull;

public class UpdateUserRoleRequestDTO {

    @NotNull(message = "Role id is required.")
    private Integer roleId;

    public UpdateUserRoleRequestDTO() {
    }

    public UpdateUserRoleRequestDTO(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}