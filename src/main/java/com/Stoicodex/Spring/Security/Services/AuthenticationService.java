package com.Stoicodex.Spring.Security.Services;

public interface AuthenticationService {
    String authenticateUser(String email, String password);
    String refreshToken(String refreshToken);

    void logout(String token);
}

