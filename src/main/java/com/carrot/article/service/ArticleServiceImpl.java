package com.carrot.article.service;

import com.carrot.article.entity.Article;
import com.carrot.attachfile.service.AttachFileService;
import com.carrot.exception.custom.OutOfDateException;
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
    public Article save(Article article) {
        //Post post = request.toPost();
        //@todo : author는 추후 account정보에서 자동으로 읽어오기, DTO에서 안받고 임시로 상수값으로 넣어줌
        article.config("Anonymous");
        return articleRepository.save(article);
    }

    @Transactional
    public Article update(Long postId, Article updateArticle) {
        Article savedArticle = articleRepository
                .findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        return articleRepository.save(savedArticle.update(updateArticle));
    }

    @Transactional
    public void delete(Long postId) {
        articleRepository.deleteById(postId);
    }

    @Transactional(readOnly = true)
    public Article get(Long postId, SearchType query) {
        Article article = articleRepository
                .findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        if (query.equals("any") && article.isExpired(LocalDateTime.now())) {
            throw new OutOfDateException();
        }

        article.incrementViewsAsync();

        return article;
    }

    @Transactional(readOnly = true)
    public List<Article> getPage(Pageable pageable) {
        return articleRepository
                .findAllValid(LocalDateTime.now(), pageable)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Article> getPageEvery(Pageable pageable) {
        return articleRepository
                .findAll(pageable)
                .toList();
    }


    public void saveWithAttach(Long postId, List<MultipartFile> attachFiles) {
        Article article = articleRepository
                .findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        for (MultipartFile file : attachFiles) {
            attachFileService.saveAttach(file, article);
        }
    }

    @Override
    public Article read(Long articleId) {
        return articleRepository.findById(articleId).get();
    }

    @Transactional
    public void incrementViewCount(Long postId, long increment) {
        Article article = articleRepository
                .findById(postId)
                .orElseThrow(EntityNotFoundException::new);
        article.incrementViewsSync(increment);
    }
}
