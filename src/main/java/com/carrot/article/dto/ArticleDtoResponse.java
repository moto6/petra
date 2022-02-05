package com.carrot.article.dto;

import com.carrot.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticleDtoResponse {
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private Long viewCount;
    private String author;

    public static ArticleDtoResponse toResponse(Article article) {
    }

    public static Page<ArticleDtoResponse> toResponseList(Page<Article> articleList) {
    }
}
