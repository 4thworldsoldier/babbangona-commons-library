package com.babbangona.commons.library.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private long userId;
    private String username;
    private String password;
    private Long tenantId;
    private List<Long> roleIds;
}
