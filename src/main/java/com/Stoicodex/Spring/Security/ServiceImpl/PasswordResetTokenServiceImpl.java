package com.Stoicodex.Spring.Security.ServiceImpl;

import com.Stoicodex.Spring.Security.Entities.User;
import com.Stoicodex.Spring.Security.Entities.VerificationToken;
import com.Stoicodex.Spring.Security.Repository.UserRepository;
import com.Stoicodex.Spring.Security.Repository.VerificationTokenRepository;
import com.Stoicodex.Spring.Security.Services.PasswordResetTokenService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
private final VerificationTokenRepository verificationTokenRepository;

private static final Logger logger = LoggerFactory.getLogger(PasswordResetTokenServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public PasswordResetTokenServiceImpl(VerificationTokenRepository verificationTokenRepository, UserRepository userRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createPasswordResetTokenForUser(String token, String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            VerificationToken passwordResetToken = new VerificationToken();
            passwordResetToken.setToken(token);
            passwordResetToken.setUser(user);
            passwordResetToken.setExpiryDate(calculateExpiryDate(15));
            // 24 hours
            verificationTokenRepository.save(passwordResetToken);
            logger.info("Password reset token created for user: {}", email);

        }

        else {
            logger.error("User with email {} not found", email);
        }
    }@Override
    public String validatePasswordResetToken(String token) {
        Optional<VerificationToken> tokenOptional = verificationTokenRepository.findByToken(token);
        if (tokenOptional.isPresent()) {
            VerificationToken passwordResetToken = tokenOptional.get();
            if (passwordResetToken.getExpiryDate().after(new Date())) {
                return "Valid";
            } else {
                logger.warn("Password reset token expired: {}", token);
                return "Expired";
            }
        } else {
            logger.error("Password reset token not found: {}", token);
            return "Invalid";
        }
    }

    @Override
    public void deletePasswordResetToken(String token) {
        Optional<VerificationToken> tokenOptional = verificationTokenRepository.findByToken(token);
        if (tokenOptional.isPresent()) {
            verificationTokenRepository.delete(tokenOptional.get());
            logger.info("Password reset token deleted: {}", token);
        } else {
            logger.warn("Password reset token not found: {}", token);
        }
    }

    @Override
    @Transactional
    public void deleteAllExpiredSincePasswordResetToken() {
        Date now = new Date();
        int deletedCount = verificationTokenRepository.deleteAllExpiredSince(now);
        logger.info("Number of expired password reset tokens deleted: {}", deletedCount);
    }
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }

}
