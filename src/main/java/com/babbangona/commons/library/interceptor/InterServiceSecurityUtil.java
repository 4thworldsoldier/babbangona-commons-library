package com.babbangona.commons.library.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InterServiceSecurityUtil {

    public static final String AUTH_HEADER_KEY = "";

    @Value("${interservice.header.auth:}")
    private String interServiceHeaderAuth;

    public String getInterServiceAuthHeaderValue() {
        return interServiceHeaderAuth;
    }

    boolean isRequestAuthorized(String headerValue) {
        return getInterServiceAuthHeaderValue().equalsIgnoreCase(headerValue);
    }
}