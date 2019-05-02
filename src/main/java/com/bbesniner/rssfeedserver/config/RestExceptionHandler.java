package com.bbesniner.rssfeedserver.config;

import com.bbesniner.rssfeedserver.entities.exceptions.CreateConflictException;
import com.bbesniner.rssfeedserver.security.jwt.InvalidJwtAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(value = {InvalidJwtAuthenticationException.class})
    public ResponseEntity invalidJwtAuthentication(final InvalidJwtAuthenticationException exception,
                                                   final WebRequest request) {
        log.debug("Handling InvalidJwtAuthenticationException : " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(value = {CreateConflictException.class})
    public ResponseEntity conflictOnCreate(final CreateConflictException exception, final WebRequest request) {
        log.debug("Handling CreateConflictException : " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
