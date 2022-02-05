package com.carrot.account.controller;

import com.carrot.account.dto.AccountDto;
import com.carrot.account.entity.Account;
import com.carrot.auth.CurrentUser;
import com.carrot.common.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountApiController {

    private final ModelMapper modelMapper;

    @GetMapping("/api/v1/account/me")
    public ApiResult<?> getAccount(@CurrentUser Account currentUser) {
        AccountDto accountDto = modelMapper.map(currentUser, AccountDto.class);
        return ApiResult.sussess(accountDto, HttpStatus.OK);
    }

}
