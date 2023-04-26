package io.petra.account.service;

import io.petra.account.domain.Account;
import io.petra.attachfile.AppImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageService {
    public Object accountInfo(Account account) {
        throw new RuntimeException();
    }

    public void changeImage(Account account, AppImage image) {
        throw new RuntimeException();
    }

    public void changeProfile(Account asIsAccount, Account toBeAccount) {
        throw new RuntimeException();
    }

    public void quit(Account account) {
        throw new RuntimeException();
    }
}
