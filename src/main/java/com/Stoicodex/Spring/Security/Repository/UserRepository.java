package com.Stoicodex.Spring.Security.Repository;

import com.Stoicodex.Spring.Security.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
    User findByUsername(String username);
}
