package com.Stoicodex.Spring.Security.Entities;

import com.Stoicodex.Spring.Security.Model.RoleName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

@JsonDeserialize (using = RoleDeserializer.class)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length =20)
            private RoleName name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;


}
