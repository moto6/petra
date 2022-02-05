package com.carrot.post.dto;

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
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private Long viewCount;
    private String author;
}
