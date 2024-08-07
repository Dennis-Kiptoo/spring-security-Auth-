/*package com.Stoicodex.Spring.Security.ServiceImpl;

import com.Stoicodex.Spring.Security.Entities.Role;
import com.Stoicodex.Spring.Security.Entities.User;
import com.Stoicodex.Spring.Security.Model.RoleName;
import com.Stoicodex.Spring.Security.Repository.RoleRepository;
import com.Stoicodex.Spring.Security.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> roleOptional = roleRepository.findByName(RoleName.ROLE_USER);
        Set<Role> roles = new HashSet<>();
        roleOptional.ifPresent(roles::add);
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        logger.info("New user registered with email: {}", user.getEmail());
        return savedUser;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
        logger.info("User saved with email: {}", user.getEmail());
    }

    @Override
    public Optional <User> findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            logger.info("User found with email: {}", email);
        } else {
            logger.warn("User not found with email: {}", email);
        }
        return user;
    }
}*/
