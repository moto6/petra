package io.petra.mail;

import io.petra.common.response.MyAppRespons;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Mail", description = "메일전송 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    // 비밀번호 찾기 이메일 발송
    @Operation(summary = "비밀번호 찾기 이메일 발송 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이메일 발송 성공"),
            @ApiResponse(responseCode = "400", description = "에러코드 : ", content = @Content(schema = @Schema(implementation = MyAppRespons.class)))
    })
    @PostMapping("/check/email")
    public ResponseEntity<MyAppRespons> sendFindPasswordMail(@RequestBody MailRequest emailDto) {
        return mailService.sendFindPasswordMail(emailDto.getEmail());
    }

    // 비밀번호 찾기 인증 코드 유효 검사
    @Operation(summary = "비밀번호 찾기 인증 코드 유효 검사 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유효한 인증 코드"),
            @ApiResponse(responseCode = "400", description = "에러코드 : ", content = @Content(schema = @Schema(implementation = MyAppRespons.class)))
    })
    @PostMapping("/check/code/{uuid}")
    public ResponseEntity<MyAppRespons> checkFindPasswordCode(@PathVariable String uuid) {
        return mailService.checkFindPasswordCode(uuid);
    }

    // 비밀번호 변경
    @Operation(summary = "비밀번호 변경 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
            @ApiResponse(responseCode = "400", description = "에러코드", content = @Content(schema = @Schema(implementation = MyAppRespons.class)))
    })
    @PostMapping("/reset/password/{uuid}")
    public ResponseEntity<MyAppRespons> resetPassword(@PathVariable String uuid, @RequestBody PasswordResetRequest passwordResetRequest) {
        return mailService.resetPassword(uuid, passwordResetRequest);
    }

    // 워크스페이스 초대 인증 코드 유효 검사 및 회원가입 유무 검사
    @Operation(summary = "워크스페이스 초대 인증 코드 유효 검사 및 회원가입 유무 검사 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "워크스페이스 초대"),
            @ApiResponse(responseCode = "400", description = "에러코드", content = @Content(schema = @Schema(implementation = MyAppRespons.class)))
    })
    @PostMapping("/check/invite/code/{uuid}")
    public ResponseEntity<InviteCodeResponse> checkInviteWorkspaceCode(@PathVariable String uuid) {
        return mailService.checkInviteWorkspaceCode(uuid);
    }

}
