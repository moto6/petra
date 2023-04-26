package com.carrot.admin;

import com.carrot.account.domain.Account;
import com.carrot.common.response.MyAppRespons;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Admin", description = "Admin API")
@RequestMapping("/api/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "전체회원 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " ~ 성공"),
            @ApiResponse(responseCode = "400", description = "에러코드 : ~", content = @Content(schema = @Schema(implementation = MyAppRespons.class)))
    })
    @GetMapping
    public String accounts() {
        List<Account> accounts = adminService.accounts();
        throw new RuntimeException();
    }

}
