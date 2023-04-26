package io.petra.account.dto;

import io.petra.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountResponseDto {
    private Long id;
    private String nickname;
    private String accountType;

    public AccountResponseDto(Account account) {
        this.id = account.getId();
        this.nickname = account.getNickname();
        //this.accountType = account.().getDescription();
    }

    public static Page<AccountResponseDto> pages(Page<Account> accounts) {
        List<AccountResponseDto> responseDtoList = accounts.stream()
                .map(AccountResponseDto::new)
                .collect(Collectors.toList());

        return new PageImpl<>(responseDtoList, accounts.getPageable(), accounts.getTotalPages());
    }
}
