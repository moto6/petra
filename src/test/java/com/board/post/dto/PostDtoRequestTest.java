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
}
