package com.carrot.article.service;

import com.carrot.attachfile.service.AttachFileService;
import com.carrot.exception.custom.OutOfDateException;
import com.carrot.article.entity.Post;
import com.carrot.article.repository.ArticleRepository;
import com.carrot.article.util.SearchType;
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
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final AttachFileService attachFileService;

    @Transactional
    public Post save(Post post) {
        //Post post = request.toPost();
        //@todo : author는 추후 account정보에서 자동으로 읽어오기, DTO에서 안받고 임시로 상수값으로 넣어줌
        post.config("Anonymous");
        return articleRepository.save(post);
    }

    @Transactional
    public Post update(Long postId, Post updatePost) {
        Post savedPost = articleRepository
                .findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        return articleRepository.save(savedPost.update(updatePost));
    }

    @Transactional
    public void delete(Long postId) {
        articleRepository.deleteById(postId);
    }

    @Transactional(readOnly = true)
    public Post get(Long postId, SearchType query) {
        Post post = articleRepository
                .findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        if (query.equals("any") && post.isExpired(LocalDateTime.now())) {
            throw new OutOfDateException();
        }

        post.incrementViewsAsync();

        return post;
    }

    @Transactional(readOnly = true)
    public List<Post> getPage(Pageable pageable) {
        return articleRepository
                .findAllValid(LocalDateTime.now(), pageable)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Post> getPageEvery(Pageable pageable) {
        return articleRepository
                .findAll(pageable)
                .toList();
    }


    public void saveWithAttach(Long postId, List<MultipartFile> attachFiles) {
        Post post = articleRepository
                .findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        for (MultipartFile file : attachFiles) {
            attachFileService.saveAttach(file, post);
        }
    }

    @Override
    public Post read(Long articleId) {
        return articleRepository.findById(articleId).get();
    }

    @Transactional
    public void incrementViewCount(Long postId, long increment) {
        Post post = articleRepository
                .findById(postId)
                .orElseThrow(EntityNotFoundException::new);
        post.incrementViewsSync(increment);
    }
}
