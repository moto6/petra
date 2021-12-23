package com.board.post.service;

import com.board.post.dto.PostDtoRequest;
import com.board.post.entity.Post;
import com.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final ModelMapper mapper;

    public Long save(PostDtoRequest request) {
        Post post = request.toPost();
        //@todo : author는 추후 account정보에서 자동으로 읽어오기, DTO에서 안받고 임시로 상수값으로 넣어줌
        post.config("Anonymous");
        return postRepository.save(post).getId();
    }

    @Transactional
    public Long update(Long postId, PostDtoRequest request) {
        Post savedPost = postRepository
                .findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        return postRepository.save(savedPost.update(request.toPost())).getId();
    }
}
