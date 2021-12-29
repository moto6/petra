package com.board.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    // OutOfDateException
    @ExceptionHandler({OutOfDateException.class})
    public ResponseEntity<?> handleAccessDeniedException(final OutOfDateException e) {
        log.warn("OutOfDateException {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
    }

}
