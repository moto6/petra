package com.board.post.dto;

import java.time.LocalDateTime;

public class PostDtoResponse {
    String title;

    String contents;

    LocalDateTime createdAt;

    Long viewCount;

    String author;
}
