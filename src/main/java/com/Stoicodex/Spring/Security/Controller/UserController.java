package com.Stoicodex.Spring.Security.Controller;

import com.Stoicodex.Spring.Security.Entities.User;
import com.Stoicodex.Spring.Security.ServiceImpl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*") // Allow all origins

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(@Lazy UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity <?> registerUser(@RequestBody User user){
        try{
            User  registeredUser = userService.registerNewUser(user);
            logger.info("User registered successfully : {}", registeredUser);
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            logger.error ("Error occured while registering user : {}", e.getMessage());
            return new ResponseEntity<>("Error occured while registering user", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        finally {
            logger.info("User registered successfully");
        }


    }

    @GetMapping("/{email}")
    public ResponseEntity <?> findUserByEmail(@PathVariable String email){
        try{
            User user = userService.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with email: " + email);

            }
            logger.info("User found successfully : {}", user);
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (UsernameNotFoundException e) {
            logger.warn("User not found with email: {}", email);
            return new ResponseEntity<>("User not found with email: " + email, HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            logger.error("Error occured while finding user : {}", e.getMessage());
            return new ResponseEntity<>("Error occured while finding user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        finally{
            logger.info("User found successfully");
        }

    }
    @PostMapping("/save")

    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            logger.info("User saved successfully with email: {}", user.getEmail());
            return new ResponseEntity<>("User saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while saving user: {}", e.getMessage());
            return new ResponseEntity<>("Error occurred while saving user", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info("Completed processing saveUser request");
        }
    }

}
