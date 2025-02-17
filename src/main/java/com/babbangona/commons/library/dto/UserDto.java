package com.babbangona.commons.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private long userId;
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Tenant ID is required")
    private Long tenantId;

    @NotEmpty(message = "At least one role ID must be provided")
    private List<Long> roleIds;
}
