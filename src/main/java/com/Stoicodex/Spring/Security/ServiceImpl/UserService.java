package com.Stoicodex.Spring.Security.ServiceImpl;

import com.Stoicodex.Spring.Security.Entities.Role;
import com.Stoicodex.Spring.Security.Entities.User;
import com.Stoicodex.Spring.Security.Model.RoleName;
import com.Stoicodex.Spring.Security.Repository.RoleRepository;
import com.Stoicodex.Spring.Security.Repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            if (role.getId() == null) {
                roleRepository.save(role);
            }
            roles.add(role);
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Transactional
    public void saveUser(User user) {
        for (Role role : user.getRoles()) {
            if (role.getId() == null) {
                roleRepository.save(role);
            }
        }
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
