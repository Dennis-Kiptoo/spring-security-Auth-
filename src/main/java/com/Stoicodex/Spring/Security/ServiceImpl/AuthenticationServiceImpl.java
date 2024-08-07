package com.Stoicodex.Spring.Security.ServiceImpl;

import com.Stoicodex.Spring.Security.Entities.User;
import com.Stoicodex.Spring.Security.Repository.UserRepository;
//import com.Stoicodex.Spring.Security.Service.AuthenticationService;
import com.Stoicodex.Spring.Security.Services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTServiceImpl jwtUtil;
    private static Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTServiceImpl jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String authenticateUser(String email, String password) {
        logger.info("Attempting to authenticate user with email: {}", email);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                logger.info("Authentication successful for user: {}", email);
                return jwtUtil.generateToken(email); // Assuming you have a method to generate JWT
            } else {
                logger.warn("Invalid password for user: {}", email);
                throw new BadCredentialsException("Invalid password");
            }
        } else {
            logger.warn("User not found with email: {}", email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }

    @Override
    public String refreshToken(String refreshToken) {
        return "New refresh token";
    }

    @Override
    public void logout(String token) {
        jwtUtil.invalidateToken(token);
    }
}