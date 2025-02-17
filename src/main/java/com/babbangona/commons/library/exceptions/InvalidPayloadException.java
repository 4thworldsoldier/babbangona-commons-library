package com.babbangona.commons.library.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidPayloadException extends RuntimeException {
    private final HttpStatus status;
    public InvalidPayloadException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
