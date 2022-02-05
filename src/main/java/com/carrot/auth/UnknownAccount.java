package com.jari.jari.common.auth;

import com.jari.jari.account.entity.Account;
import com.jari.jari.account.entity.AccountType;

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
