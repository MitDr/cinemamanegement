package com.project.cinemamanagement.Service;

import com.project.cinemamanagement.Entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRole();
    Role addRole(Role role);
    Role getRoleById(Long roleId);
    Role updateRole(Long roleId, Role role);
    Role deleteRole(Long roleId);

}
