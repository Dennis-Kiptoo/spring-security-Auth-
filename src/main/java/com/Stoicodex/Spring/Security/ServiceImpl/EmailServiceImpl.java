package com.Stoicodex.Spring.Security.ServiceImpl;

import com.Stoicodex.Spring.Security.Services.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JavaMailSender mailSender;
    private final Environment env;

    public EmailServiceImpl(JavaMailSender mailSender, Environment env) {
        this.mailSender = mailSender;
        this.env = env;
    }

    @Override
    public void sendVerificationEmail(String email, String token) {
        String subject = "Account Verification";
        String verificationUrl = env.getProperty("app.verification.url") + "?token=" + token;
        String body = "<p>Click the link below to verify your account:</p>" +
                "<a href=\"" + verificationUrl + "\">Verify Account</a>";
        sendHtmlMessage(email, subject, body);
    }

    @Override
    public void sendPasswordResetEmail(String email, String token) {
        String subject = "Password Reset";
        String resetUrl = env.getProperty("app.reset.url") + "?token=" + token;
        String body = "<p>Click the link below to reset your password:</p>" +
                "<a href=\"" + resetUrl + "\">Reset Password</a>";
        sendHtmlMessage(email, subject, body);
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
            logger.info("HTML email sent to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to send HTML email to: {}", to, e);
        }
    }

}



