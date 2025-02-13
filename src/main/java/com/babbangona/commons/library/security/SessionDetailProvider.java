package com.babbangona.commons.library.security;

import com.babbangona.commons.library.dto.SessionDetail;

public interface SessionDetailProvider {
    
    SessionDetail getSession(String sessionKey, String deviceId);

    SessionDetail getOfflineSession(String sessionKey);

    String getUnauthenticatedMessage();

    String getUnauthorizedMessage();

    String getExpiredMessage();
}
