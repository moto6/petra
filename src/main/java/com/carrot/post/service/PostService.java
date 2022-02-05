package com.carrot.post.service;

import com.carrot.post.entity.Post;
import com.carrot.post.util.SearchType;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
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
