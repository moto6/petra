package com.carrot.admin;

import com.carrot.account.domain.Account;
import com.carrot.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AccountRepository accountRepository;


    public List<Account> accounts() {
        return accountRepository.findAll();
    }
}
