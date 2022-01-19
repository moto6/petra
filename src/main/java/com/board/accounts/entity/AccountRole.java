package com.board.accounts.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountRole {

    ADMIN("관리자"),
    USER("일반사용자"),
    GUEST("손님");

    private final String title;
}
