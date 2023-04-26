package com.carrot.mail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "스웨거설명")
@Getter
public class InviteCodeResponse {
    public InviteCodeResponse(Boolean isValidUser, Long expiredAt) {
        this.isValidUser = isValidUser;
        this.expiredAt = expiredAt;
    }

    @Schema(description = "초대 코드 링크로 들어온 사용자가 회원인지 ")
    private final Boolean isValidUser;
    @Schema(description = "유효기간 ")
    private final Long expiredAt;


    public Long getExpiredAt() {
        return expiredAt;
    }

    public Boolean getValidUser() {
        return isValidUser;
    }
}
