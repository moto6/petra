package com.board.accounts.entity;


import com.board.accounts.dto.AccountDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Set;

@Builder
@Entity
@Getter
@Setter
@Table(
        name = "account",
        indexes = {@Index(name = "email", columnList = "email", unique = true)}
)
@NoArgsConstructor
public class Account extends TimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "real_name")
    private String realName;


    @Column(name = "email", unique = true)
    private String email;

    private String password;

    private String picture;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    private Set<AccountRole> roles;

    //@todo 이부분 Entity가 DTO를 의존하지 않도록 수정하고 싶은데 어떻게 고치면 좋을지 고민됩니다
    public static Account createUser(AccountDto accountDto, PasswordEncoder passwordEncoder) {
        Account newcomer = Account.builder()
                .nickname(accountDto.getNickname())
                .email(accountDto.getEmail())
                .build();
        //newcomer.password = passwordEncoder.encode(accountDto.getPassword());
        //@todo : token만 사용할껀데 여기는 어떻게 처리하지??
        newcomer.password = passwordEncoder.encode(accountDto.getNickname());
        newcomer.roles.add(AccountRole.ADMIN);
        return newcomer;
    }

    public Account update(String name, String picture) {
        this.realName = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        if(this.roles.contains(AccountRole.ADMIN) ) {
            return AccountRole.ADMIN.getKey();
        }

        if(this.roles.contains(AccountRole.USER) ) {
            return AccountRole.USER.getKey();
        }

        return AccountRole.GUEST.getKey();
    }
}


