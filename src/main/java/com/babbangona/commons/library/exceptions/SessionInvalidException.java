package com.babbangona.commons.library.exceptions;

import java.io.Serial;

public class SessionInvalidException extends InterceptorException {

    @Serial
    private static final long serialVersionUID = 348923924349320567L;

    public SessionInvalidException(String message){
        super(message);
    }
}
