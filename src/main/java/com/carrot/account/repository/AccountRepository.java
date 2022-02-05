package com.carrot.account.repository;

import com.carrot.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    //Optional<Account> findByIdAndAccountType(Long accountId, x accountType);
}
