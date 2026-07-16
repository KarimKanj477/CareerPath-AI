package com.careerpath.careerpathai.service;
import com.careerpath.careerpathai.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import  java.util.List;
public interface RoleService {

    Page<Role> getAllRoles(Pageable pageable);

    Role getRoleById(Integer id);

    Role saveRole(Role role);

    void deleteRole(Integer id);
    List<Role> searchRolesByName(String name);
}

