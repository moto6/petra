package com.carrot.article.exception;

public class OutOfDateArticleExposeException extends RuntimeException {
    public OutOfDateArticleExposeException() {
        super("expired");
    }
}
