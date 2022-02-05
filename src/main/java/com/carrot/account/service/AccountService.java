package com.carrot.account.service;

import com.carrot.account.dto.AccountResponseDto;
import com.carrot.account.domain.Account;
import com.carrot.account.repository.AccountRepository;
import com.carrot.exception.custom.NoSuchAccountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static com.carrot.auth.UnknownAccount.guestAuth;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    //private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Transactional
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @PostConstruct
    public void registry() {

        Account account = accountRepository.save(guestAuth());
        log.info("registry GUEST: {}", new AccountResponseDto(account));
    }

    public Account create(Account account) {

        return accountRepository.save(account);
    }

    public Account read(Long id) {

        return accountRepository
                .findById(id)
                .orElseThrow(NoSuchAccountException::new);
    }


    public Page<Account> readPage(Pageable pageable) {

        return accountRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Account authInfoParser(String authInfo) {
        //todo 리팩토링 필요, 테스트 필요
        try {
            String[] authStrings = authInfo.split(" ");
            Long accountId = Long.parseLong(authStrings[1]);
            String accountType = authStrings[0];
            return this.getAccount(accountId, accountType);
        } catch (NoSuchAccountException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            log.info("잘못된 회원 인증헤더 정보 {}", authInfo);
        }
        return guestAuth();
    }

    private Account getAccount(Long accountId, String accountType) {
        //todo
        return new Account();
    }
}
