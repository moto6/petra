package com.carrot.auth;


import com.carrot.account.entity.Account;

public final class UnknownAccount {
    private static final Account GUEST = Account.builder()
            .nickname("비회원")
            .accountType(AccountType.Guest)
            .quit(Boolean.TRUE)
            .build();

    private UnknownAccount() {
    }

    public static Account guestAuth() {
        return GUEST;
    }
}
