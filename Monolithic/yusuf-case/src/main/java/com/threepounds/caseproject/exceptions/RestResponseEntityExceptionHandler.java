package com.threepounds.caseproject.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.crossstore.ChangeSetPersister.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgument(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(NotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> badCredentials(BadCredentialsException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accessDenied() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.FORBIDDEN.value());
        errorResponse.setErrorMessage("Forbidden");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(value = EmailCheckException.class)
    public ResponseEntity<ErrorResponse> existingEmail(EmailCheckException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }



    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> generalException(Exception e) {
        e.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setErrorMessage("Internal Server Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }



}
