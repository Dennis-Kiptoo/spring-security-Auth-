package com.Stoicodex.Spring.Security.Services;

import com.Stoicodex.Spring.Security.Entities.User;

import java.util.Optional;

public interface UserService {
    User registerNewUser(User user);
    void saveUser(User user);
    Optional<User> findByEmail(String email);
}
