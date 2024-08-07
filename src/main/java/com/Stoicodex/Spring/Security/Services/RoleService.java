package com.Stoicodex.Spring.Security.Services;

import com.Stoicodex.Spring.Security.Entities.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(String roleName);

    Role createRole(Role role);

    Role updateRole(Long roleId, Role role);

    void deleteRole(Long roleId);
   // Iterable<Role> findAllRoles();

    Iterable<Role> getAllRoles();
}
