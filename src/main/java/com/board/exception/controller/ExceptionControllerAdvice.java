package com.board.exception.controller;

import com.board.common.ApiResult;
import com.board.exception.custom.FavoriteDuplicationException;
import com.board.exception.custom.GuestForbiddenException;
import com.board.exception.custom.InvalidQueryException;
import com.board.exception.custom.NoSuchAccountException;
import com.board.exception.custom.NoSuchAccountTypeException;
import com.board.exception.custom.OutOfDateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler({OutOfDateException.class})
    public ResponseEntity<?> OutOfDateExceptionHandler(final OutOfDateException e) {
        log.warn("OutOfDateException {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
    }

    @ExceptionHandler({InvalidQueryException.class})
    public ResponseEntity<?> InvalidQueryExceptionHandler(final InvalidQueryException e) {
        log.warn("InvalidQueryException {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
    }

    @ExceptionHandler({NoSuchAccountException.class, NoSuchAccountTypeException.class})
    public ResponseEntity<?> accountAuthException(final RuntimeException e) {

        log.info("accountAuthException : [{}]", e.getMessage());
        ApiResult<?> result = ApiResult.fail(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(result);
    }

    @ExceptionHandler({GuestForbiddenException.class})
    public ResponseEntity<?> guestForbiddenException(final RuntimeException e) {

        log.info("guestForbiddenException : [{}]", e.getMessage());
        ApiResult<?> result = ApiResult.fail(HttpStatus.UNAUTHORIZED, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(result);
    }


    @ExceptionHandler({FavoriteDuplicationException.class})
    public ResponseEntity<?> favoriteDuplicationException(final RuntimeException e) {

        log.info("favoriteDuplicationException, 좋아요는 여러번 눌러도 요청성공을 리턴: [{}]", e.getMessage());
        ApiResult<?> result = ApiResult.sussess(e.getMessage(), HttpStatus.NO_CONTENT);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(result);
    }


    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> entityNotFoundException(final RuntimeException e) {

        log.info("entityNotFoundException : [{}]", e.getMessage());
        ApiResult<?> result = ApiResult.sussess(e.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(result);
    }


}
