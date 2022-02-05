package com.carrot.article.service;

import com.carrot.article.entity.Post;
import com.carrot.article.util.SearchType;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {
    Post save(Post post);

    Post update(Long postId, Post updatePost);

    void delete(Long postId);

    Post get(Long postId, SearchType query);

    List<Post> getPage(Pageable pageable);

    List<Post> getPageEvery(Pageable pageable);

    @Deprecated
    void saveWithAttach(Long postId, List<MultipartFile> attachFiles);

    Post read(Long articleId);
}
