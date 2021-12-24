package com.board.exception;

public class OutOfDateException extends RuntimeException {
    public OutOfDateException() {
        super("expired");
    }
}
