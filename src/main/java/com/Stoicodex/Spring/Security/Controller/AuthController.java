package com.Stoicodex.Spring.Security.Controller;

import com.Stoicodex.Spring.Security.Entities.User;
import com.Stoicodex.Spring.Security.Services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        logger.info("Received login request for user: {}", user.getEmail());
        try {
            String token = authenticationService.authenticateUser(user.getEmail(), user.getPassword());
            logger.info("User logged in successfully: {}", user.getEmail());
            return ResponseEntity.ok(token);
        } catch (UsernameNotFoundException e) {
            logger.warn("Login failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadCredentialsException e) {
            logger.warn("Login failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during login: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String token) {
        try {
            authenticationService.logout(token);
            logger.info("User logged out successfully");
            return new ResponseEntity<>("User logged out successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while logging out: {}", e.getMessage());
            return new ResponseEntity<>("Error occurred while logging out", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info("Completed processing logout request");
        }
    }
}