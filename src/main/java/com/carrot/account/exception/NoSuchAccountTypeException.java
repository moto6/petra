package com.carrot.account.exception;

public class NoSuchAccountTypeException extends RuntimeException {
    public NoSuchAccountTypeException() {

        super("잘못된 계정타입입니다 메뉴얼을 확인해주세요");
    }
}
