package io.petra.account.exception;

public class GuestForbiddenException extends RuntimeException {
    public GuestForbiddenException() {

        super("GUEST 는 호출할 수 없는 API 입니다");
    }
}
