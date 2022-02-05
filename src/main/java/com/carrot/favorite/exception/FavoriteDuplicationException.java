package com.carrot.favorite.exception;

public class FavoriteDuplicationException extends RuntimeException {
    public FavoriteDuplicationException() {

        super("좋아요는 한 계정이 한 글에 한 번만 누를 수 있습니다");
    }
}
