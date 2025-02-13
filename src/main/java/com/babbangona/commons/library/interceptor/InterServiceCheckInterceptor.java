package com.babbangona.commons.library.interceptor;

import com.babbangona.commons.library.annotations.InterService;
import com.babbangona.commons.library.exceptions.InterServiceCheckException;
import com.babbangona.commons.library.utils.HandlerMethodUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class InterServiceCheckInterceptor implements HandlerInterceptor {
    private static final String INVALID_HEADER_MESSAGE = "Invalid service authorization header";
    private static final String UNAUTHORIZED_HEADER_MESSAGE = "Unauthorized service request";

    private final InterServiceSecurityUtil interService;

    public InterServiceCheckInterceptor(InterServiceSecurityUtil interService) {
        this.interService = interService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!HandlerMethodUtil.supports(handler)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        InterService annotation = HandlerMethodUtil.getMethodAnnotation(InterService.class, handlerMethod);
        if (annotation == null) {
            return true;
        }

        String authHeaderValue = HandlerMethodUtil.getHeaderParam(request, InterServiceSecurityUtil.AUTH_HEADER_KEY);

        if (ObjectUtils.isEmpty(authHeaderValue)) {
            throw new InterServiceCheckException(INVALID_HEADER_MESSAGE);
        }

        boolean isAuthorizedRequest = interService.isRequestAuthorized(authHeaderValue);
        if (!isAuthorizedRequest) {
            throw new InterServiceCheckException(UNAUTHORIZED_HEADER_MESSAGE);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
