package com.babbangona.commons.library.exceptions;

import com.babbangona.commons.library.dto.response.BaseResponse;
import com.babbangona.commons.library.logging.CommonsLogger;
import com.babbangona.commons.library.logging.CommonsLoggerFactory;
import com.babbangona.commons.library.utils.ResponseConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final CommonsLogger log = CommonsLoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InterceptorException.class)
    public final ResponseEntity<BaseResponse> handleVerveMobileInterceptorException(SessionInvalidException exception) {
        log.error("SESSION EXCEPTION", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new BaseResponse(ResponseConstants.ERROR_CODE, exception.getMessage()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse> handleVerveMobileCustomException(CustomException exception) {
        log.info("VERVE MOBILE EXCEPTION", exception.getMessage());
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

}
