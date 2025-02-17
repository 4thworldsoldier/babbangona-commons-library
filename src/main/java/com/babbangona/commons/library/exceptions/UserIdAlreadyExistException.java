package com.babbangona.commons.library.exceptions;

public class UserIdAlreadyExistException extends RuntimeException {
    public UserIdAlreadyExistException(String userIdIsAlreadyTaken) {
        super(userIdIsAlreadyTaken);
    }
}
