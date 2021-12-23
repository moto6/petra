package com.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDtoResponse {
    String title;

    String contents;

    LocalDateTime createdAt;

    Long viewCount;

    String author;
}
