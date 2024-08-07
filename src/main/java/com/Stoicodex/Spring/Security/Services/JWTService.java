package com.Stoicodex.Spring.Security.Services;

public interface JWTService {
    String generateToken(String email);
    String validateToken(String token);
    String getEmailFromToken(String token);
    String generateRefreshToken(String email);
    String validateRefreshToken(String token);
    String getEmailFromRefreshToken(String token);
    void invalidateToken(String token);
}
