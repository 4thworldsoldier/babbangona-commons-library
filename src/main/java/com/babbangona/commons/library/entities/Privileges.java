package com.babbangona.commons.library.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import java.io.Serial;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "portal_role_permissions")
@Getter
@Setter
public class Privileges extends BaseEntity {

    private String name;
    @ManyToOne
    private UserRole role;

    private Boolean canView;
    private Boolean canCreate;
    private Boolean canUpdate;
    private Boolean canDelete;
}
