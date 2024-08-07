package com.Stoicodex.Spring.Security.Entities;

import com.Stoicodex.Spring.Security.Model.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne (targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn (nullable = false, name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenType tokenType;

    private Date expiryDate;


}
