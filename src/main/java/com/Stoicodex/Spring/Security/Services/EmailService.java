package com.Stoicodex.Spring.Security.Services;

public interface EmailService {
    void sendVerificationEmail(String email, String token);
    void sendPasswordResetEmail(String email, String token);
}
