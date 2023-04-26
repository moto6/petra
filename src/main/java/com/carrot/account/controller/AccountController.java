package com.carrot.account.controller;

import com.carrot.account.domain.Account;
import com.carrot.account.dto.AccountDto;
import com.carrot.account.dto.AccountRequest;
import com.carrot.account.dto.AccountResponseDto;
import com.carrot.account.service.AccountService;
import com.carrot.auth.AuthUser;
import com.carrot.auth.CurrentUser;
import com.carrot.common.response.MyAppRespons;
import com.carrot.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
public class AccountController {

    private final AccountService accountService;

    private final ModelMapper modelMapper;

    @Operation(summary = "나는누구 여긴어디 API(여기다가 메뉴 : 회원>>내정보 이런정보 넣어주셈",description = "아 오늘은 일찍자야하는데.. 코딩너무 맛있고 어쩌고 저쩌고에 대한 설명을 쓰")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "실패코드 : 4001, 4002", content = @Content(schema = @Schema(implementation = MyAppRespons.class)))
    })
    @GetMapping("/me")
    public ResponseEntity<?> getAccount(@CurrentUser Account currentUser) {

        AccountDto accountDto = modelMapper.map(currentUser, AccountDto.class);
        return ApiResult.success(accountDto, HttpStatus.OK).responseBuild();
    }

    @PostMapping
    public ResponseEntity<?> creatAccount(@RequestBody AccountRequest request) {

        Account account = accountService.create(request.to());
        return ApiResult.success(new AccountResponseDto(account), HttpStatus.CREATED).responseBuild();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> readAccount(@PathVariable Long id) {

        Account account = accountService.read(id);
        return ApiResult.success(new AccountResponseDto(account), HttpStatus.OK).responseBuild();
    }

    @GetMapping()
    public ResponseEntity<?> readList(Pageable pageable, @AuthUser Account account) {

        log.info("Account 정보 : {}", account.toString());
        Page<Account> accounts = accountService.readPage(pageable);
        return ApiResult.success(AccountResponseDto.pages(accounts), HttpStatus.OK).responseBuild();
    }
}
