package com.carrot.article.exception;

public class InvalidArticleQueryException extends RuntimeException {
    public InvalidArticleQueryException(String string) {
        super("Invalid Query String [" + string + "] is unknown, please check API guide or Contact us");
    }
}
