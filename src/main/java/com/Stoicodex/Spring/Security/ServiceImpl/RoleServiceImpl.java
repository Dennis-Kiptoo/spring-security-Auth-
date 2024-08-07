package com.Stoicodex.Spring.Security.ServiceImpl;

import com.Stoicodex.Spring.Security.Entities.Role;
import com.Stoicodex.Spring.Security.Services.RoleService;
import com.Stoicodex.Spring.Security.Model.RoleName;
import com.Stoicodex.Spring.Security.Repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByName(String roleName) {
        try {
            RoleName roleEnum = RoleName.valueOf(roleName);
            return roleRepository.findByName(RoleName.valueOf(roleName));
        } catch (IllegalArgumentException e) {
            logger.error("Role not found: {}", roleName, e);
            return Optional.empty();
        }
    }

    @Override
    public Role createRole (Role role){
        Optional <Role> existingRole = roleRepository.findByName(role.getName());
        if (existingRole.isPresent()){
            throw new IllegalArgumentException("Role already exists: " + role.getName());
        }

        return roleRepository.save(role);
    }


    @Override
    public Role updateRole(Long roleId, Role role) {
        // Check if role exists
        if (!roleRepository.existsById(roleId)) {
            throw new IllegalArgumentException("Role not found with ID: " + roleId);
        }
        role.setId(roleId);
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        if (!roleRepository.existsById(roleId)) {
            throw new IllegalArgumentException("Role not found with ID: " + roleId);
        }
        roleRepository.deleteById(roleId);
    }

    @Override
    public Iterable<Role> getAllRoles() {
        return roleRepository.findAll();
    }






}