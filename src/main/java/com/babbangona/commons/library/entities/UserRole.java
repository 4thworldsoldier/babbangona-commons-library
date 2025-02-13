package com.babbangona.commons.library.entities;

import com.babbangona.commons.library.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serial;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "user_roles")
@Getter
@Setter
public class UserRole extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 8344877776966952420L;
    private String name;
    private String description;
    private Boolean isActive;
}
