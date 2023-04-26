package com.carrot.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Schema(description = "회원정보 응답")
@Getter
@AllArgsConstructor
@Data
public class AccountResponse {
    @Schema(description = "회원 이름")
    private final String userName;
    @Schema(description = "회원 id")
    private final long id;
    @Schema(description = "회원 상태 메세지")
    private final String userDesc;
    @Schema(description = "회원 프로필 이미지")
    private final String userImage;
    @Schema(description = "회원 직업")
    private final String userJob;

    public AccountResponse(Object accountInfo) {
        throw new RuntimeException();
    }
}
