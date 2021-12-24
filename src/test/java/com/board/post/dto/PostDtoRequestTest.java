package com.board.post.dto;

import java.time.LocalDateTime;

public class PostDtoRequestTest {

    public static final PostDtoRequest request1 = PostDtoRequest
            .builder()
            .title("1_어린왕자")
            .contents("1_작은별에 착륙하다")
            .validFrom(LocalDateTime.now())
            .validUntil(LocalDateTime.now().plusYears(100))
            .build();

    public static final PostDtoRequest request2 = PostDtoRequest
            .builder()
            .title("2_해리포터 혼혈왕자")
            .contents("2_스네이프교수님")
            .validFrom(LocalDateTime.now().minusYears(100))
            .validUntil(LocalDateTime.now().minusYears(1))
            .build();

    public static final PostDtoRequest request3 = PostDtoRequest
            .builder()
            .title("3_스프링부트")
            .contents("3_스프링은 IOC 컨테이너가 Bean 을 DI 해주는 OOP 프레임워크 입니다")
            .validFrom(null)
            .validUntil(null)
            .build();

    public static final PostDtoRequest request4 = PostDtoRequest
            .builder()
            .title("4_JPA DDD AOC")
            .contents("4_JPA-JPA-JPA-JPA-JPA-JPA-JPA-JPA-")
            .validFrom(null)
            .validUntil(LocalDateTime.now())
            .build();

}
