package com.board.post.service;

import com.board.post.dto.PostDtoRequest;
import com.board.post.entity.Post;
import com.board.post.util.SearchType;
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
}
