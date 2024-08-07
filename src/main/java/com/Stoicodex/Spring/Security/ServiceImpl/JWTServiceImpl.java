package com.Stoicodex.Spring.Security.ServiceImpl;

import com.Stoicodex.Spring.Security.Services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JWTServiceImpl implements JWTService {
    @Value("${spring.security.jwt.secret-key}")
    private String secret;

    @Value("${spring.security.jwt.expiration-time}")
    private long expiration;

    @Value("${spring.security.jwt.refresh-expiration-time}")
    private long refreshExpiration;

    private Set<String> tokenBlacklist = new HashSet<>();

    @Override
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public String validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return "Valid";
        } catch (Exception e) {
            return "Invalid";
        }
    }

    @Override
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    @Override
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public String validateRefreshToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return "Valid";
        } catch (Exception e) {
            return "Invalid";
        }
    }

    @Override
    public String getEmailFromRefreshToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    @Override
    public void invalidateToken(String token) {
        tokenBlacklist.add(token);
    }
}