package com.Stoicodex.Spring.Security.Services;

public interface OTPService {
    String generateOTP(String key);
    boolean validateOTP(String key, String otp);
}
