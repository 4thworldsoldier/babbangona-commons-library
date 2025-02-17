package com.babbangona.commons.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TenantDto {
    @NotNull(message = "Tenant ID is required")
    private Long tenantId;
    @NotNull(message = "Username is required")
    private String username;
}
