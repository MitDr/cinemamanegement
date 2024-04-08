package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.Role;
import com.project.cinemamanagement.Exception.DataNotFoundException;
import com.project.cinemamanagement.Repository.RoleRepository;
import com.project.cinemamanagement.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new DataNotFoundException("Role not found"));
    }

    @Override
    public Role updateRole(Long roleId, Role role) {
        Role updateRole = roleRepository.findById(roleId).orElseThrow(() -> new DataNotFoundException("Role not found"));

        updateRole.setRoleName(role.getRoleName());
        updateRole.setDescription(role.getDescription());
        updateRole.setUser(role.getUser());
        return roleRepository.save(updateRole);
    }

    @Override
    public Role deleteRole(Long roleId) {
        Role deleteRole = roleRepository.findById(roleId).orElseThrow(() -> new DataNotFoundException("Role not found"));
        roleRepository.delete(deleteRole);
        return deleteRole;
    }
}
