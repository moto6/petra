package com.carrot.account.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountRole {

    ADMIN("관리자", "ADMIN"),
    USER("일반사용자", "USER"),
    GUEST("손님", "GUEST");

    private final String title;
    private final String key;
}
