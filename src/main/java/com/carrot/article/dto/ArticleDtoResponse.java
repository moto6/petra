package com.carrot.article.dto;

import com.carrot.article.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
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

        return ArticleDtoResponse.builder()
                .title(article.getTitle())
                .contents(article.getContents())
                .createdAt(article.getCreatedAt())
                .viewCount(article.getViewCount())
                .author(article.getAuthor())
                .build();
    }

    public static Page<ArticleDtoResponse> toResponseList(Page<Article> articleList) {

        List<ArticleDtoResponse> responseList = articleList.stream()
                .map(ArticleDtoResponse::toResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(responseList, articleList.getPageable(), articleList.getTotalPages());
    }
}
