package com.babbangona.commons.library.exceptions;

import java.io.Serial;

public class InterceptorException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7056816418253942377L;

    public InterceptorException(String message) {
        super(message);
    }
}
