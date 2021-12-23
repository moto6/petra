package com.board.post.dto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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


}
