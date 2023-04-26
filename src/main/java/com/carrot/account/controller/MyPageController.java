package com.carrot.account.controller;

import com.carrot.account.domain.Account;
import com.carrot.account.dto.AccountRequest;
import com.carrot.account.dto.AccountResponse;
import com.carrot.account.service.MypageService;
import com.carrot.attachfile.AppImage;
import com.carrot.common.response.MyAppRespons;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "MyPage", description = "MyPage API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/mypage")
public class MyPageController {

    private final MypageService mypageService;

    @Operation(summary = "마이페이지 회원정보 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원정보 조회 성공")
    })
    @GetMapping
    public AccountResponse getUserInfo(Account account) {
        return new AccountResponse(mypageService.accountInfo(account));
    }

    @Operation(summary = "회원 프로필 이미지 변경~ API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " 프사변경~ 성공"),
            @ApiResponse(responseCode = "400", description = "에러코드 : ~", content = @Content(schema = @Schema(implementation = MyAppRespons.class)))
    })
    @PostMapping
    public void changeImage(Account account, AppImage image) {
        mypageService.changeImage(account, image);
        throw new RuntimeException();
    }

    @Operation(summary = "회원정보 변경 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원정보 변경 ~ 성공"),
            @ApiResponse(responseCode = "400", description = "에러코드 : ~", content = @Content(schema = @Schema(implementation = MyAppRespons.class)))
    })
    @PatchMapping
    public String changeProfile(Account account, AccountRequest accountRequest) {
        mypageService.changeProfile(account, accountRequest.to());
        throw new RuntimeException();
    }

    @Operation(summary = "회원탈퇴~ API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "탈퇴요청~ 성공"),
            @ApiResponse(responseCode = "400", description = "에러코드 : ~", content = @Content(schema = @Schema(implementation = MyAppRespons.class)))
    })
    @PostMapping
    public String quitAccount(Account account) {
        mypageService.quit(account);
        throw new RuntimeException();
    }



}
