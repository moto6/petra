package com.carrot.article.service;

import com.carrot.article.entity.Article;
import com.carrot.article.util.SearchType;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {
    Article save(Article article);

    Article update(Long postId, Article updateArticle);

    void delete(Long postId);

    Article get(Long postId, SearchType query);

    List<Article> getPage(Pageable pageable);

    List<Article> getPageEvery(Pageable pageable);

    @Deprecated
    void saveWithAttach(Long postId, List<MultipartFile> attachFiles);

    Article read(Long articleId);
}
