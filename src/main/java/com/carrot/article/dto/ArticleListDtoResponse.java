package com.carrot.article.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleListDtoResponse {
    private List<ArticleDtoResponse> list;
    private Pageable pageable;

    public ArticleListDtoResponse(List<ArticleDtoResponse> list, Pageable pageable) {
        this.list = list;
        this.pageable = pageable;
    }
}
