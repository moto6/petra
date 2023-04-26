package io.petra.auth;


import io.petra.account.domain.Account;
import io.petra.account.domain.AccountRole;

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
