package com.board.post.service;

import com.board.post.dto.PostDtoRequest;
import com.board.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    Post save(PostDtoRequest request);

    Post update(Long postId, PostDtoRequest request);

    void delete(Long postId);

    Post get(Long postId,String query);

    List<Post> getPage(Pageable pageable);

    List<Post> getPageAny(Pageable pageable);

    @Deprecated
    void saveWithAttach(PostDtoRequest postDtoRequest, List<MultipartFile> attachFiles);

}
