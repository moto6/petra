package com.carrot.account.controller;

import com.carrot.account.dto.AccountDto;
import com.carrot.account.dto.AccountRequestDto;
import com.carrot.account.dto.AccountResponseDto;
import com.carrot.account.domain.Account;
import com.carrot.account.service.AccountService;
import com.carrot.auth.AuthUser;
import com.carrot.auth.CurrentUser;
import com.carrot.common.apiresult.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

@Slf4j
@RequestMapping("api/v1/account")
@RestController
@RequiredArgsConstructor
public class AccountApiController {

    private final AccountService accountService;

    private final ModelMapper modelMapper;

    @GetMapping("/me")
    public ApiResult<?> getAccount(@CurrentUser Account currentUser) {
        AccountDto accountDto = modelMapper.map(currentUser, AccountDto.class);
        return ApiResult.success(accountDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> creatAccount(@RequestBody AccountRequestDto request) {
        Account account = accountService.create(request.createAccount());
        ApiResult<?> result = ApiResult.success(new AccountResponseDto(account), HttpStatus.CREATED);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> readAccount(@PathVariable Long id) {
        Account account = accountService.read(id);
        ApiResult<?> result = ApiResult.success(new AccountResponseDto(account), HttpStatus.OK);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping()
    public ResponseEntity<?> readAccounts(Pageable pageable, @AuthUser Account account) {
        Page<Account> accounts = accountService.readPage(pageable);
        ApiResult<?> result = ApiResult.success(AccountResponseDto.pages(accounts), HttpStatus.OK);
        log.info("Account 정보 : {}", account.toString());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

}
