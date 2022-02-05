package com.carrot.article.service;

import com.carrot.article.entity.Article;
import com.carrot.article.util.SearchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {
    Article save(Article article);

    Article update(Long articleId, Article updateArticle);

    void delete(Long articleId);

    Article get(Long articleId );

    Article query(Long articleId, SearchType searchType);

    Page<Article> getPage(Pageable pageable);

    Page<Article> getPageEvery(Pageable pageable);

    @Deprecated
    void saveWithAttach(Long articleId, List<MultipartFile> attachFiles);

    Article read(Long articleId);
}
