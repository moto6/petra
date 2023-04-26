package io.petra.account.exception;

public class NoSuchAccountException extends RuntimeException {
    public NoSuchAccountException() {

        super("계정 정보를 찾을 수 없습니다");
    }
}
