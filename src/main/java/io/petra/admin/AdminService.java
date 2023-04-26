package io.petra.admin;

import io.petra.account.domain.Account;
import io.petra.account.repository.AccountRepository;
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
