package com.babbangona.commons.library.exceptions;

import java.io.Serial;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends Exception {

    @Serial
    private static final long serialVersionUID = 6307040533308351964L;

    private final HttpStatus status;

    public CustomException() {
        this("Oops! Something Went Wrong, Please try again.");
    }

    public CustomException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
