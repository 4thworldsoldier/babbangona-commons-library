package com.babbangona.commons.library.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "role")
@Getter
@Setter
public class Role {
    @Serial
    private static final long serialVersionUID = 8344877776966952420L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "is_active", columnDefinition="TINYINT")
    private Boolean isActive;

    @ManyToMany
    @JoinTable(name = "role_privilege",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Set<Privilege> privileges = new HashSet<>();

    @Column(name = "is_tenant", columnDefinition="TINYINT")
    private Boolean isTenantRole;
}
