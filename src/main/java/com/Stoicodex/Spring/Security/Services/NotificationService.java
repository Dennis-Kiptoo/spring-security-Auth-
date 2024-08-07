package com.Stoicodex.Spring.Security.Services;

public interface NotificationService {
   // void sendVerificationEmail(String email, String token);
  //  void sendPasswordResetEmail(String email, String token);
    void sendEmailNotification(String to, String body, String subject);
    void sendSMSNotification(String to, String body);
}
