package com.board.exception.custom;

public class OutOfDateException extends RuntimeException {
    public OutOfDateException() {
        super("expired");
    }
}
