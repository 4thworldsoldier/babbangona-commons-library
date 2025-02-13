package com.babbangona.commons.library.exceptions;

import java.io.Serial;

public class InterServiceCheckException extends InterceptorException {
    @Serial
    private static final long serialVersionUID = 9042945708510620984L;

    public InterServiceCheckException(String message){
        super(message);
    }
}
