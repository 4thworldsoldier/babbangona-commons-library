package com.babbangona.commons.library.exceptions.handler;

import com.babbangona.commons.library.logging.CommonsLogger;
import com.babbangona.commons.library.logging.CommonsLoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final CommonsLogger log = CommonsLoggerFactory.getLogger(GlobalExceptionHandler.class);

  /*  @ExceptionHandler(InterceptorException.class)
    public final ResponseEntity<BaseResponse> handleInterceptorException(SessionInvalidException exception) {
        log.error("SESSION EXCEPTION", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new BaseResponse(ResponseConstants.ERROR_CODE, exception.getMessage()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse> handleCustomException(CustomException exception) {
        log.info("CUSTOM EXCEPTION", exception.getMessage());
        return ResponseEntity.status(exception.getStatus())
                .body(new BaseResponse(ResponseConstants.ERROR_CODE, exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.info("REQUEST VALIDATION EXCEPTION", exception.getMessage());
        return ResponseEntity.badRequest()
                .body(new BaseResponse(ResponseConstants.ERROR_CODE, exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<BaseResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        log.info("REQUEST VALIDATION EXCEPTION", exception.getMessage());
        return ResponseEntity.badRequest()
                .body(new BaseResponse(ResponseConstants.ERROR_CODE, exception.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleException(Exception exception) {
        log.info("REQUEST VALIDATION EXCEPTION", exception.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleGeneralException(Exception ex) {
        log.error("Unexpected error occurred: {} : {}", ex.getMessage(), ex);
        return buildErrorResponse("Unexpected error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    *//**
     * Helper method to build a standardized error response.
     *
     * @param message The error message to include in the response.
     * @param status  The HTTP status to return.
     * @return A ResponseEntity containing the BaseResponse and the HTTP status.
     *//*
    private ResponseEntity<BaseResponse<Void>> buildErrorResponse(String message, HttpStatusCode status) {
        // Create an error response with no data (using Void as the type)
        BaseResponse<Void> response = new BaseResponse<>(ResponseConstants.ERROR_CODE, message, null);
        return new ResponseEntity<>(response, status);
    }
*/
   /* @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException
            (BadCredentialsException badCredentialsException, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", HttpStatus.UNAUTHORIZED.value());
        body.put("timestamp", LocalDateTime.now());
        body.put("message", badCredentialsException.getMessage());
        body.put("path", webRequest.getContextPath());
        body.put("sessionId", webRequest.getSessionId());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidPayloadException.class)
    public ResponseEntity<?> handleInvalidPayloadException
            (InvalidPayloadException invalidPayloadException, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", HttpStatus.BAD_REQUEST.value());
        body.put("timestamp", LocalDateTime.now());
        body.put("message", invalidPayloadException.getMessage());
        body.put("path", webRequest.getContextPath());
        body.put("sessionId", webRequest.getSessionId());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIdAlreadyExistException.class)
    public ResponseEntity<?> handleUserIdAlreadyExistException
            (UserIdAlreadyExistException userIdAlreadyExistException, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", HttpStatus.BAD_REQUEST.value());
        body.put("timestamp", LocalDateTime.now());
        body.put("message", userIdAlreadyExistException.getMessage());
        body.put("path", webRequest.getContextPath());
        body.put("sessionId", webRequest.getSessionId());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleSignatureException
            (SignatureException signatureException, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", HttpStatus.UNAUTHORIZED.value());
        body.put("timestamp", LocalDateTime.now());
        body.put("message", signatureException.getMessage());
        body.put("path", webRequest.getContextPath());
        body.put("sessionId", webRequest.getSessionId());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
*/
}
