package com.Stoicodex.Spring.Security.Services;

public interface VerificationTokenService {

    void createVerification(String token, String email);
    String validateVerificationToken(String token);
    void deleteVerificationToken(String token);
    void deleteAllExpiredSince();
    void createPasswordResetTokenForUser(String token, String email);
    String validatePasswordResetToken(String token);
    void deletePasswordResetToken(String token);
    void deleteAllExpiredSincePasswordResetToken();
}
