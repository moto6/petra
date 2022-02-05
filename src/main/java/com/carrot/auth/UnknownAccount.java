package com.carrot.auth;


import com.carrot.account.entity.Account;
import com.carrot.account.entity.AccountRole;

public final class UnknownAccount {
    private static final Account GUEST_ACCOUNT = Account.builder()
            .nickname("비회원")
            .accountType(AccountRole.GUEST)
            .quit(Boolean.TRUE)
            .build();

    private UnknownAccount() {
    }

    public static Account guestAuth() {
        return GUEST_ACCOUNT;
    }
}
