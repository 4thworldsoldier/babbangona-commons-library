package com.babbangona.commons.library.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = 3918822049045880468L;
    private String clientIp;
    private String sessionId;
    private String institutionCD;
    private String deviceId;
    private String locale = "en";
    private AuthenticatedUser principal;
    private String[] permissions;
    private Boolean isActive;

    public boolean hasAnyPermission (String[] permissions) {
       if (permissions == null || permissions.length == 0) {
           // No special permissions required
           return true;
       }
       List<String> all = Arrays.asList(permissions);
       for (String permission : this.permissions) {
           if (all.contains(permission)) {
               return true;
           }
       }
       return false;
    }
    
    public void merge (SessionDetail detail) {
        if (detail == null) {
            return;
        }
        setIsActive(detail.getIsActive());
        setSessionId(detail.getSessionId());
        setDeviceId(detail.getDeviceId());
        setPermissions(detail.getPermissions());
        setPrincipal(detail.getPrincipal());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionDetail that = (SessionDetail) o;
        return Objects.equals(clientIp, that.clientIp) && Objects.equals(sessionId, that.sessionId) && Objects.equals(institutionCD, that.institutionCD) && Objects.equals(deviceId, that.deviceId) && Objects.equals(locale, that.locale) && Objects.equals(principal, that.principal) && Arrays.equals(permissions, that.permissions) && Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(clientIp, sessionId, institutionCD, deviceId, locale, principal, isActive);
        result = 31 * result + Arrays.hashCode(permissions);
        return result;
    }

    @Override
    public String toString() {
        return "SessionDetail{" +
                "clientIp='" + clientIp + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", institutionCD='" + institutionCD + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", locale='" + locale + '\'' +
                ", principal=" + principal +
                ", permissions=" + Arrays.toString(permissions) +
                ", isActive=" + isActive +
                '}';
    }
}