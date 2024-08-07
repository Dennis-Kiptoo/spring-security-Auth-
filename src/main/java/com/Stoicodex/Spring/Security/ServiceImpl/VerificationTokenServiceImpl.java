package com.Stoicodex.Spring.Security.ServiceImpl;
import com.Stoicodex.Spring.Security.Services.VerificationTokenService;

import com.Stoicodex.Spring.Security.Entities.User;
import com.Stoicodex.Spring.Security.Entities.VerificationToken;
import com.Stoicodex.Spring.Security.Repository.UserRepository;
import com.Stoicodex.Spring.Security.Repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class VerificationTokenServiceImpl implements  VerificationTokenService{
    private static final Logger logger = LoggerFactory.getLogger(VerificationTokenServiceImpl.class);
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    @Autowired
    public VerificationTokenServiceImpl(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository){
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
    }
       @Override
        public void createVerification(String token, String email) {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                VerificationToken verificationToken = new VerificationToken();
                verificationToken.setToken(token);
                verificationToken.setUser(user);
                verificationToken.setExpiryDate(calculateExpiryDate(24 * 60)); // 24 hours
                verificationTokenRepository.save(verificationToken);
                logger.info("Verification token created for user: {}", email);
            } else {
                logger.error("User with email {} not found", email);
            }
        }

    @Override
    public String validateVerificationToken(String token) {
        Optional<VerificationToken> tokenOptional = verificationTokenRepository.findByToken(token);
        if (tokenOptional.isPresent()) {
            VerificationToken verificationToken = tokenOptional.get();
            if (verificationToken.getExpiryDate().after(new Date())) {
                return "Valid";
            } else {
                logger.warn("Verification token expired: {}", token);
                return "Expired";
            }
        } else {
            logger.error("Verification token not found: {}", token);
            return "Invalid";
        }
    }

    @Override
    public void deleteVerificationToken(String token) {
        Optional<VerificationToken> tokenOptional = verificationTokenRepository.findByToken(token);
        tokenOptional.ifPresent(verificationToken -> {
            verificationTokenRepository.delete(verificationToken);
            logger.info("Verification token deleted: {}", token);
        });
    }

    @Override
    @Transactional
    public void deleteAllExpiredSince() {
        Date now = new Date();
        verificationTokenRepository.findAll().forEach(token -> {
            if (token.getExpiryDate().before(now)) {
                verificationTokenRepository.delete(token);
                logger.info("Expired verification token deleted: {}", token.getToken());
            }
        });
    }

    @Override
    public void createPasswordResetTokenForUser(String token, String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            VerificationToken passwordResetToken = new VerificationToken();
            passwordResetToken.setToken(token);
            passwordResetToken.setUser(user);
            passwordResetToken.setExpiryDate(calculateExpiryDate(15)); // 15 minutes
            verificationTokenRepository.save(passwordResetToken);
            logger.info("Password reset token created for user: {}", email);
        } else {
            logger.error("User with email {} not found", email);
        }
    }

    @Override
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
