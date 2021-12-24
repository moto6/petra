package com.board.post.service;

public class OutOfDateException extends RuntimeException {
    public OutOfDateException() {
        super("expired");
    }
}
