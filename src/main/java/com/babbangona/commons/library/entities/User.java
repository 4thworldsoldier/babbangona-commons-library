package com.babbangona.commons.library.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the user database table.
 *
 */
@Getter
@Setter
@Entity(name = "users")
public class User  {

    @Serial
    private static final long serialVersionUID = 867755336300305063L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @ManyToOne(optional=true)
    @JoinColumn(nullable= true, name = "tenant_id")
    private Tenant tenant;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> userRoles = new HashSet<>();


}
