package com.babbangona.commons.library.interceptor;

import com.babbangona.commons.library.annotations.Secured;
import com.babbangona.commons.library.dto.SessionDetail;
import com.babbangona.commons.library.exceptions.SessionInvalidException;
import com.babbangona.commons.library.factory.SessionDetailFactory;
import com.babbangona.commons.library.logging.CommonsLogger;
import com.babbangona.commons.library.logging.CommonsLoggerFactory;
import com.babbangona.commons.library.security.SessionDetailProvider;
import com.babbangona.commons.library.utils.HandlerMethodUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 20)
public class SecurityInterceptor implements HandlerInterceptor {

    private final SessionDetailFactory sessionDetailFactory;

    private final SessionDetailProvider sessionDetailProvider;

    private static final CommonsLogger log = CommonsLoggerFactory.getLogger(SecurityInterceptor.class);

    public SecurityInterceptor(SessionDetailFactory sessionDetailFactory, SessionDetailProvider sessionDetailProvider) {
        this.sessionDetailFactory = sessionDetailFactory;
        this.sessionDetailProvider = sessionDetailProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!HandlerMethodUtil.supports(handler)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Secured annotation = HandlerMethodUtil.getMethodAnnotation(Secured.class, handlerMethod);
        if (annotation == null) {
            return true;
        }

        String deviceID = HandlerMethodUtil.getHeaderParam(request,"DEVICE_ID");
        String sessionID = HandlerMethodUtil.getHeaderParam(request, "SESSION_ID");

        if (annotation.optional() && isEmpty(sessionID)) {
            return true;
        }

        if (isEmpty(sessionID)) {
            throw new SessionInvalidException("User SessionId is required");
        }

        SessionDetail sessionDetail = sessionDetailProvider.getSession(sessionID, deviceID);
        if (sessionDetail == null) {
            throw new SessionInvalidException(sessionDetailProvider.getUnauthenticatedMessage());
        }

        log.debug("SESSION DETAIL RETRIEVED: {}", sessionDetail);
        if (!sessionDetail.getIsActive()) {
            throw new SessionInvalidException(sessionDetailProvider.getExpiredMessage());
        }

        sessionDetail.setSessionId(sessionID);
        sessionDetail.setDeviceId(deviceID);

        // Check for annotation at class and signatureMethods level
        String[] required = annotation.permissions();
        if (!sessionDetail.hasAnyPermission(required)) {
            throw new SessionInvalidException(sessionDetailProvider.getUnauthorizedMessage());
        }

        sessionDetailFactory.setSessionDetail(sessionDetail);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
