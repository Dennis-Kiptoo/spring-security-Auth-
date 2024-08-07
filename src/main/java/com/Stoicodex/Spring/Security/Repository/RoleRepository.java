package com.Stoicodex.Spring.Security.Repository;

import com.Stoicodex.Spring.Security.Entities.Role;
import com.Stoicodex.Spring.Security.Model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName name);

}
