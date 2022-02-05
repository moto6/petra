package com.carrot.exception.custom;

public class OutOfDateException extends RuntimeException {
    public OutOfDateException() {
        super("expired");
    }
}
