package com.board.post.service;

import com.board.attachfile.service.AttachFileService;
import com.board.exception.OutOfDateException;
import com.board.post.dto.PostDtoRequest;
import com.board.post.entity.Post;
import com.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final AttachFileService attachFileService;

    @Transactional
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

    @Transactional
    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }

    @Transactional(readOnly = true)
    public Post read(Long postId) {

        Post post = postRepository
                .findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        if (!post.isValidPeriod(LocalDateTime.now())) {
            throw new OutOfDateException();
        }
        return post;
    }

    @Transactional(readOnly = true)
    public Post readAny(Long postId) {
        return postRepository
                .findById(postId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Post> readAll(Pageable pageable) {
        return postRepository
                .findAllValid(LocalDateTime.now(), pageable)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Post> readAnyAll(Pageable pageable) {
        return postRepository
                .findAll(pageable)
                .toList();
    }


    public void saveWithAttach(PostDtoRequest postDtoRequest, List<MultipartFile> attachFiles) {
        Long postId = this.save(postDtoRequest);
        Post post = postRepository
                .findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        for (MultipartFile file : attachFiles) {
            attachFileService.saveAttach(file, post);
        }
    }

    @Transactional
    public void incrementViewCount(Long postId, long increment) {
        Post post = postRepository
                .findById(postId)
                .orElseThrow(EntityNotFoundException::new);
        post.incrementViews(increment);
    }
}