package com.board.post.dto;

import java.time.LocalDateTime;

public class PostDtoRequestTest {

    public static final PostDtoRequest request1 = PostDtoRequest
            .builder()
            .title("어린왕자")
            .contents("작은별에 착륙하다")
            .validFrom(LocalDateTime.now())
            .validUntil(LocalDateTime.MAX)
            .build();

    public static final PostDtoRequest request2 = PostDtoRequest
            .builder()
            .title("해리포터 혼혈왕자")
            .contents("스네이프교수님")
            .validFrom(LocalDateTime.MIN)
            .validUntil(LocalDateTime.now())
            .build();

    public static final PostDtoRequest request3 = PostDtoRequest
            .builder()
            .title("스프링부트")
            .contents("스프링은 IOC 컨테이너가 Bean 을 DI 해주는 OOP 프레임워크 입니다")
            .validFrom(null)
            .validUntil(null)
            .build();

    public static final PostDtoRequest request4 = PostDtoRequest
            .builder()
            .title("JPA DDD AOC")
            .contents("JPA-JPA-JPA-JPA-JPA-JPA-JPA-JPA-")
            .validFrom(null)
            .validUntil(LocalDateTime.now())
            .build();

}
