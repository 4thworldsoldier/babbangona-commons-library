package com.babbangona.commons.library.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Map;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;

public final class HandlerMethodUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    private HandlerMethodUtil() {}

    public static boolean supports(Object handler) {
        return handler instanceof HandlerMethod;
    }

    public static <A extends Annotation> A getMethodAnnotation(Class<A> annotationClass, HandlerMethod handlerMethod){
        A annotation = handlerMethod.getMethodAnnotation(annotationClass);

        if (annotation == null) {
            annotation = AnnotationUtils.findAnnotation(handlerMethod.getMethod().getDeclaringClass(), annotationClass);
        }

        return annotation;
    }

    public static String getHeaderParam(HttpServletRequest request, String headerParamName){
        String headerValue = request.getHeader(headerParamName);

        if(ObjectUtils.isEmpty(headerValue)) {
            Map<String, String[]> parameterMap = request.getParameterMap();

            if(parameterMap.containsKey(headerParamName)) {
                String[] values = parameterMap.get(headerParamName);

                if(values != null && values.length > 0) {
                    headerValue = values[0];
                }
            }
        }
        return headerValue;
    }

    public static String getIpFromRequest (HttpServletRequest request) {
        String address = request.getHeader("X-Forwarded-For");
        address = address == null || address.length() == 0 ? request.getHeader("X-FORWARDED-FOR") : address;
        address = address == null || address.length() == 0 ? request.getHeader("x-forwarded-for") : address;
        address = address == null || address.length() == 0 ? request.getRemoteAddr() : address;
        return address;
    }

    public static <T> T getFromJson(String json, Class<T> clazz) throws JsonProcessingException {
        return getObjectMapper().readValue(json, clazz);
    }

    public static <T> T getFromJson(String json, TypeReference<T> clazz) throws JsonProcessingException {
        return getObjectMapper().readValue(json, clazz);
    }

    public static <T> T getFromJson(JsonNode json, Class<T> clazz) throws JsonProcessingException {
        return getObjectMapper().treeToValue(json, clazz);
    }

    public static String toJson(Object json) throws JsonProcessingException {
        return getObjectMapper().writeValueAsString(json);
    }

    public static JsonNode toJsonNode(Object json) {
        return getObjectMapper().valueToTree(json);
    }

    private static ObjectMapper getObjectMapper() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}