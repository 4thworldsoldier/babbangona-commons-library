package com.babbangona.commons.library.security;

import com.babbangona.commons.library.dto.AuthenticatedUser;
import com.babbangona.commons.library.dto.InMemorySessionData;
import com.babbangona.commons.library.dto.SessionDetail;
import com.babbangona.commons.library.cache.RedisClientService;
import com.babbangona.commons.library.logging.CommonsLogger;
import com.babbangona.commons.library.logging.CommonsLoggerFactory;
import com.babbangona.commons.library.utils.HandlerMethodUtil;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class InMemorySessionProvider implements SessionDetailProvider {
    private static final CommonsLogger log = CommonsLoggerFactory.getLogger(InMemorySessionProvider.class);
    private static final String UNAUTHENTICATED_MESSAGE = "Authentication Failure";
    private static final String UNAUTHORIZED_MESSAGE = "Unauthorized Access";
    private static final String SESSION_EXPIRY_MESSAGE = "Session expired. Please login again";
    private static final long SESSION_TIMEOUT = 1000;

    private final RedisClientService redis;

    public InMemorySessionProvider(RedisClientService redis) {
        this.redis = redis;
    }

    @Override
    public SessionDetail getSession(String sessionID, String deviceID) {
        SessionDetail result = new SessionDetail();
        InMemorySessionData sd = null;

        String decryptedSessionID;
        try {
            decryptedSessionID = CryptoUtil.decrypt256(CryptoUtil.AES_KEY, sessionID);
        } catch (Exception ex) {
            log.error("SESSIONID DECRYPTION FAILED", ex);
            decryptedSessionID = sessionID;
        }

        String[] sessionIDArr = decryptedSessionID.split("@");
        Long customerId = null;
        String sessionDeviceId = null;
        try {
            customerId = Long.parseLong(sessionIDArr[0]);
            sessionDeviceId = sessionIDArr[1];
        } catch (Exception ex) {
            log.error("ERROR", ex);
        }
        if (customerId == null || sessionDeviceId == null) {
            return null;
        }
        if (!sessionDeviceId.equals(deviceID)) {
            return null;
        }
        String key = String.valueOf(customerId);
        try {
            String value = redis.getValue(key);
            if (value != null) {
                sd = HandlerMethodUtil.getFromJson(value, InMemorySessionData.class);
                if (sd != null) {
                    if (!sd.getSessionID().equals(sessionID)) {
                        return null;
                    }
                    if (isSessionExpired(sd)) {
                        result.setIsActive(false);
                        return result;
                    }

                    // need to update the last active time here
                    sd.setLastActiveTime(new Date().getTime());
                    String json = HandlerMethodUtil.toJson(sd);
                    redis.setKeyValueWithLongTimeout(key, json, sd.getTimeoutMilliSeconds() / SESSION_TIMEOUT);

                    String accountKey = "CUSTOMER_ACCOUNT_" + key;
                    LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(sd.getTimeoutMilliSeconds() / SESSION_TIMEOUT);
                    Date expiryDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                    redis.setKeyTimeoutInDate(accountKey, expiryDate);
                }
            }
        } catch (Exception ex) {
            log.error("EXCEPTION IN SESSION PHASING FOR SESSION ID: {}", sessionID, ex);
        }

        if (sd == null) {
            return null;
        }
        if (!sd.getDeviceID().equals(deviceID)) {
            return null;
        }

        result.setDeviceId(deviceID);
        result.setSessionId(decryptedSessionID);
        result.setIsActive(true);
        result.setInstitutionCD(sd.getInstitutionCD());

        AuthenticatedUser user = new AuthenticatedUser();
        user.setId(sd.getCustomerId());
        user.setFullName(sd.getFullName());
        user.setLastLoginTime(new Date(sd.getLogonTime()));
        user.setUserName(sd.getUserName());
        user.setPhoneNumber(sd.getCustomerPhoneNumber());
        user.setEmail(sd.getCustomerEmail());

        result.setPrincipal(user);
        return result;
    }

    @Override
    public SessionDetail getOfflineSession(String sessionID) {
        SessionDetail result = new SessionDetail();
        InMemorySessionData sd = null;
        try {
            String value = redis.getValue(sessionID);
            if (value != null) {
                sd = HandlerMethodUtil.getFromJson(value, InMemorySessionData.class);
                if (sd != null) {
                    if (!sd.getSessionID().equals(sessionID)) {
                        return null;
                    }
                    if (isSessionExpired(sd)) {
                        result.setIsActive(false);
                        return result;
                    }

                    // need to update the last active time here
                    sd.setLastActiveTime(new Date().getTime());
                    String json = HandlerMethodUtil.toJson(sd);
                    redis.setKeyValueWithLongTimeout(sessionID, json, sd.getTimeoutMilliSeconds() / SESSION_TIMEOUT);

                    String accountKey = "accounts_"+sessionID;
                    LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(sd.getTimeoutMilliSeconds() / SESSION_TIMEOUT);
                    Date expiryDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
                    redis.setKeyTimeoutInDate(accountKey, expiryDate);
                }
            }
        } catch (Exception ex) {
            log.error("EXCEPTION IN SESSION PHASING FOR SESSION ID: " + sessionID, ex);
        }

        if (sd == null) {
            return null;
        }
        result.setSessionId(sessionID);
        result.setIsActive(true);
        result.setInstitutionCD(sd.getInstitutionCD());

        AuthenticatedUser user = new AuthenticatedUser();
        user.setId(sd.getCustomerId());
        user.setFullName(sd.getFullName());
        user.setLastLoginTime(new Date(sd.getLogonTime()));
        user.setUserName(sd.getUserName());
        user.setPhoneNumber(sd.getCustomerPhoneNumber());
        user.setEmail(sd.getCustomerEmail());
        result.setPrincipal(user);
        return result;
    }

    private boolean isSessionExpired(InMemorySessionData sd) {
        return (Math.abs(new Date().getTime() - sd.getLastActiveTime()) >= sd.getTimeoutMilliSeconds());
    }

    @Override
    public String getUnauthenticatedMessage() {
        return UNAUTHENTICATED_MESSAGE;
    }

    @Override
    public String getUnauthorizedMessage() {
        return UNAUTHORIZED_MESSAGE;
    }

    @Override
    public String getExpiredMessage() {
        return SESSION_EXPIRY_MESSAGE;
    }

}
