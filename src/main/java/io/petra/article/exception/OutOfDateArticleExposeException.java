package io.petra.article.exception;

public class OutOfDateArticleExposeException extends RuntimeException {
    public OutOfDateArticleExposeException() {
        super("expired");
    }
}
