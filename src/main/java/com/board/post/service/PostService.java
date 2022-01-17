package com.board.post.service;

import com.board.post.dto.PostDtoRequest;
import com.board.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    Long save(PostDtoRequest request);

    Long update(Long postId, PostDtoRequest request);

    void delete(Long postId);

    Post read(Long postId);

    Post readAny(Long postId);

    List<Post> readAll(Pageable pageable);

    List<Post> readAnyAll(Pageable pageable);

    @Deprecated
    void saveWithAttach(PostDtoRequest postDtoRequest, List<MultipartFile> attachFiles);

}
