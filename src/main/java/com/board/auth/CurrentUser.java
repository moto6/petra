package com.board.auth;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER) // 타겟은 파라미터에만 붙이는 어노테이션임
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account")
public @interface CurrentUser {
    // 익명 사용자인 경우에는 null로, 익명 사용자가 아닌 경우에는 실제 account 객체로
    // Principal 을 다이나믹 하게 꺼내기 위해 @CurrentUser 생성
    // 코드출처 : https://pupupee9.tistory.com/137
    // @todo 배운점 : 내가 Annotation 만들줄 모른다는 메타인지 추가
}
