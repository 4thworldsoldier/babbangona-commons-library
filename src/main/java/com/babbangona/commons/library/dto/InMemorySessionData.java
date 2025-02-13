package com.babbangona.commons.library.dto;

import com.babbangona.commons.library.enums.Status;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InMemorySessionData implements Serializable {

    @Serial
    private static final long serialVersionUID = 8655794043946866556L;
    private long customerId;
    private String userName;
    private String fullName;
    private String deviceID;
    private String regDeviceID;
    private String sessionID;
    private String institutionCD;
    private long timeoutMilliSeconds;
    private long logonTime;
    private long lastActiveTime;
    private String customerBankId;
    private String customerPhoneNumber;
    private String customerEmail;
    private Status profileStatus;
}
