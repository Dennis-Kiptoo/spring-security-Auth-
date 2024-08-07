package com.Stoicodex.Spring.Security.Repository;

import com.Stoicodex.Spring.Security.Entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
@Repository


public interface  VerificationTokenRepository extends JpaRepository <VerificationToken, String> {
    Optional<VerificationToken> findByToken(String token);
    void deleteByToken(String token);
    void deleteAllByExpiryDateLessThan(java.util.Date expiryDate);

    @Modifying
    @Query("DELETE FROM VerificationToken vt WHERE vt.expiryDate < :now")
    int deleteAllExpiredSince(@Param("now") Date now);

    int deleteByExpiryDateBefore(Date now);
}
