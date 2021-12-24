package com.board.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class ExceptionController {
    Logger log = LoggerFactory.getLogger(getClass());

    // OutOfDateException
    @ExceptionHandler({ OutOfDateException.class })
    public ResponseEntity<?> handleAccessDeniedException(final OutOfDateException e) {
        log.warn("OutOfDateException {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
    }
}
