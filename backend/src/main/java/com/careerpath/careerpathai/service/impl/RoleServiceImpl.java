package com.careerpath.careerpathai.service.impl;
import com.careerpath.careerpathai.entity.Role;
import com.careerpath.careerpathai.repository.RoleRepository;
import com.careerpath.careerpathai.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import com.careerpath.careerpathai.exception.RoleNotFoundException;
import com.careerpath.careerpathai.exception.RoleAlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    public RoleServiceImpl(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;

    }


    @Override
    public Page<Role> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public Role getRoleById(Integer id) {

        return roleRepository.findById(id)
                .orElseThrow(() ->
                        new RoleNotFoundException("Role with id " + id + " was not found.")
                );

    }

    @Override
    public Role saveRole(Role role) {

        if (roleRepository.existsByName(role.getName())) {
            throw new RoleAlreadyExistsException("Role " + role.getName() + " already exists.");
        }

        return roleRepository.save(role);
    }

    // FIX (new method): the controller used to call saveRole() for updates
    // too, which always threw RoleAlreadyExistsException unless the name
    // was also changed (since the role's own name already "exists"). This
    // loads the existing row, checks for name collisions against OTHER
    // roles only, and updates in place — same pattern as
    // CareerServiceImpl.updateCareer.
    @Override
    public Role updateRole(Integer id, Role role) {

        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() ->
                        new RoleNotFoundException("Role with id " + id + " was not found.")
                );

        if (roleRepository.existsByNameAndIdNot(role.getName(), id)) {
            throw new RoleAlreadyExistsException(
                    "Another role with name " + role.getName() + " already exists."
            );
        }

        existingRole.setName(role.getName());
        existingRole.setDescription(role.getDescription());

        return roleRepository.save(existingRole);
    }

    @Override
    public void deleteRole(Integer id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role with id " + id + " was not found.")
                );

        roleRepository.delete(role);

    }
    @Override
    public List<Role> searchRolesByName(String name) {
        return roleRepository.findByNameContainingIgnoreCase(name);
    }
}
