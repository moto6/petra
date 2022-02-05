package com.jari.jari.account.controller;

import com.jari.jari.account.dto.AccountRequestDto;
import com.jari.jari.account.dto.AccountResponseDto;
import com.jari.jari.account.entity.Account;
import com.jari.jari.account.service.AccountService;
import com.jari.jari.common.auth.AuthUser;
import com.jari.jari.common.result.ApiResult;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/account")
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @PostMapping
    public ResponseEntity<?> creatAccount(@RequestBody AccountRequestDto request) {
        Account account = accountService.create(request.createAccount());
        ApiResult<?> result = ApiResult.sussess(new AccountResponseDto(account), HttpStatus.CREATED);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> readAccount(@PathVariable Long id) {
        Account account = accountService.read(id);
        ApiResult<?> result = ApiResult.sussess(new AccountResponseDto(account), HttpStatus.OK);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping()
    public ResponseEntity<?> readAccounts(Pageable pageable, @AuthUser Account account) {
        Page<Account> accounts = accountService.readPage(pageable);
        ApiResult<?> result = ApiResult.sussess(AccountResponseDto.pages(accounts), HttpStatus.OK);
        log.info("Account 정보 : {}", account.toString());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

}
