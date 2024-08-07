package com.Stoicodex.Spring.Security.Controller;

import com.Stoicodex.Spring.Security.Entities.Role;
import com.Stoicodex.Spring.Security.Services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/role")

public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        try {
            Role createdRole = roleService.createRole(role);
            logger.info("Role created successfully: {}", createdRole);
            return ResponseEntity.ok(createdRole);
        } catch (IllegalArgumentException e) {
            logger.warn("Role already exists: {}", role.getName());
            return new ResponseEntity<>("Role already exists: " + role.getName(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error occurred while creating role: {}", e.getMessage());
            return new ResponseEntity<>("Error occurred while creating role", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info("Completed processing createRole request");
        }
    }

    @GetMapping("/{roleName}")
    public ResponseEntity<?> findRoleByName(@PathVariable String roleName) {
        try {
            Optional<Role> role = roleService.findByName(roleName);
            if (role.isEmpty()) {
                return new ResponseEntity<>("Role not found with name: " + roleName, HttpStatus.NOT_FOUND);
            }
            logger.info("Role found successfully: {}", role.get());
            return new ResponseEntity<>(role.get(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while finding role: {}", e.getMessage());
            return new ResponseEntity<>("Error occurred while finding role", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info("Completed processing findRoleByName request");
        }
    }


    @PutMapping("/{roleId}")
    public ResponseEntity <?> updateRole (@PathVariable Long roleId, @RequestBody Role role){
        try{
            Role updatedRole = roleService.updateRole(roleId, role);
            logger.info("Role updated successfully: {}", updatedRole);
            return ResponseEntity.ok(updatedRole);
        }
        catch (Exception e){
            logger.error("Error occurred while updating role: {}", e.getMessage());
            return new ResponseEntity<>("Error occurred while updating role", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        finally {
            logger.info("Completed processing updateRole request");
        }

    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable Long roleId) {
        try {
            roleService.deleteRole(roleId);
            logger.info("Role deleted successfully with ID: {}", roleId);
            return new ResponseEntity<>("Role deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while deleting role: {}", e.getMessage());
            return new ResponseEntity<>("Error occurred while deleting role", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info("Completed processing deleteRole request");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        try {
            Iterable<Role> roles = roleService.getAllRoles();
            logger.info("Roles retrieved successfully");
            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving roles: {}", e.getMessage());
            return new ResponseEntity<>("Error occurred while retrieving roles", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info("Completed processing getAllRoles request");
        }
    }
}
