package com.babbangona.commons.library.exceptions.handler;

import com.babbangona.commons.library.dto.response.BaseResponse;
import com.babbangona.commons.library.exceptions.InvalidPayloadException;
import com.babbangona.commons.library.exceptions.UserIdAlreadyExistException;
import com.babbangona.commons.library.utils.ResponseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(InvalidPayloadException.class)
    public ResponseEntity<BaseResponse<Void>> handleInvalidPayloadException(InvalidPayloadException ex) {
        logger.error("Invalid Payload Exception Occurred", ex.getMessage());
        HttpStatusCode status = getHttpStatusOrDefault(ex.getStatus().value());
        return buildErrorResponse("Invalid Payload Exception: " + ex.getMessage(), status);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        logger.error("Username NotFound Exception Occurred", ex.getMessage());
        HttpStatusCode status = getHttpStatusOrDefault(401);
        return buildErrorResponse("Username NotFound Exception: " + ex.getMessage(), status);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseResponse<Void>> handleBadCredentialsException(BadCredentialsException ex) {
        logger.error("Bad Credential Exception Occurred", ex.getMessage());
        HttpStatusCode status = getHttpStatusOrDefault(401);
        return buildErrorResponse("Bad Credential Exception: " + ex.getMessage(), status);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<BaseResponse<Void>> handleGeneralException(AuthorizationDeniedException ex) {
        logger.error("Unexpected error occurred: {} : {}", ex.getMessage(), ex);
        HttpStatusCode status = getHttpStatusOrDefault(403);
        return buildErrorResponse("AuthorizationDenied Exception: " + ex.getMessage(), status);
    }

    @ExceptionHandler(UserIdAlreadyExistException.class)
    public ResponseEntity<BaseResponse<Void>> handleUserIdAlreadyExistException(UserIdAlreadyExistException ex) {
        logger.error("User Already Exist Exception Occurred", ex.getMessage());
        HttpStatus status = getHttpStatusOrDefault(ex.getStatus().value());
        return buildErrorResponse("User Already Exist Exception: " + ex.getMessage(), status);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<BaseResponse<Void>> handleHttpClientErrorException(HttpClientErrorException ex) {
        logger.error("HTTP Client Error occurred: {}", ex.getMessage());
        HttpStatusCode status = ex.getStatusCode();
        return buildErrorResponse("HTTP error: " + ex.getMessage(), status);
    }

/*    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<BaseResponse<Void>> handleGeneralException(AuthorizationDeniedException ex) {
        logger.error("Unexpected error occurred: {} : {}", ex.getMessage(), ex);
        return buildErrorResponse("Unexpected Authentication error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + " " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        logger.error(errors);
        return buildErrorResponse("Validation failed: " + errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleGeneralException(Exception ex) {
        logger.error("Unexpected error occurred: {} : {}", ex.getMessage(), ex);
        return buildErrorResponse("Unexpected error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Helper method to build a standardized error response.
     *
     * @param message The error message to include in the response.
     * @param status  The HTTP status to return.
     * @return A ResponseEntity containing the BaseResponse and the HTTP status.
     */
    private ResponseEntity<BaseResponse<Void>> buildErrorResponse(String message, HttpStatusCode status) {
        // Create an error response with no data (using Void as the type)
        BaseResponse<Void> response = new BaseResponse<>(ResponseConstants.ERROR_CODE, message, null);
        return new ResponseEntity<>(response, status);
    }

    /**
     * Utility method to return a valid HTTP status or default to INTERNAL_SERVER_ERROR.
     *
     * @param statusCode The HTTP status code to check.
     * @return A valid HttpStatus or INTERNAL_SERVER_ERROR if the provided code is null or invalid.
     */
    private HttpStatus getHttpStatusOrDefault(Integer statusCode) {
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        HttpStatus status = HttpStatus.resolve(statusCode);
        return (status != null) ? status : HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
