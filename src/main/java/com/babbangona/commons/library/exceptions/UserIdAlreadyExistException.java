package com.babbangona.commons.library.exceptions;

import org.springframework.http.HttpStatus;

public class UserIdAlreadyExistException extends RuntimeException {
    private final HttpStatus status;
    public UserIdAlreadyExistException(String userIdIsAlreadyTaken, HttpStatus status) {
        super(userIdIsAlreadyTaken);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
