package com.board.exception;

public class InvalidQueryException extends RuntimeException {
    public InvalidQueryException(String string) {
        super("Invalid Query String : [" + string + "] , please check API guide or Contact us");
    }
}
