package com.babbangona.commons.library.dto;

import com.babbangona.commons.library.enums.Status;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticatedUser implements Serializable {
    @Serial
    private static final long serialVersionUID = -8711689521999558997L;
    private Long id;
    private String userName;
    private String fullName;
    private Date lastLoginTime;
    private String phoneNumber;
    private String email;
    private String token;
}
