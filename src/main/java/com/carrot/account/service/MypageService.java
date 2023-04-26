package com.carrot.account.service;

import com.carrot.account.domain.Account;
import com.carrot.attachfile.AppImage;
import org.springframework.stereotype.Service;

@Service
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
