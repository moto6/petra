package com.board.accounts.dto;

import com.board.accounts.entity.AccountRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String nickname;
    private String email;
    private Set<AccountRole> roles = new HashSet<>();
}
