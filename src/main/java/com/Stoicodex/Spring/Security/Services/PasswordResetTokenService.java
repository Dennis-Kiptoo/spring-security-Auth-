package com.Stoicodex.Spring.Security.Services;

public interface PasswordResetTokenService {

        void createPasswordResetTokenForUser(String token, String email);
        String validatePasswordResetToken(String token);
        void deletePasswordResetToken(String token);
        void deleteAllExpiredSincePasswordResetToken();
}
