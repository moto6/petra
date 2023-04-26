package com.carrot.account.dto;

import com.carrot.account.domain.Account;
import com.carrot.attachfile.AppImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountRequest {
    private String nickname;
    //private AccountType accountType;
    private AppImage appImage;
    private String introduceMessage;

    public Account to() {
        return Account.builder()
                .nickname(nickname)
                //.accountType(accountType)
                .authentication("NONE")
                .quit(Boolean.FALSE)
                .build();
    }
}
