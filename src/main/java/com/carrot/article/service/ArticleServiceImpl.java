package com.carrot.article.service;

import com.carrot.article.entity.Article;
import com.carrot.attachfile.service.AttachFileService;
import com.carrot.exception.custom.OutOfDateException;
import com.carrot.article.repository.ArticleRepository;
import com.carrot.article.util.SearchType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public Article update(Long articleId, Article updateArticle) {
        Article savedArticle = articleRepository
                .findById(articleId)
                .orElseThrow(EntityNotFoundException::new);

        return articleRepository.save(savedArticle.update(updateArticle));
    }

    @Transactional
    public void delete(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    @Override
    public Article get(Long articleId) {
        return articleRepository
                .findById(articleId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Article query(Long articleId, SearchType query) {
        Article article = articleRepository
                .findById(articleId)
                .orElseThrow(EntityNotFoundException::new);

        if (query.equals("any") && article.isExpired(LocalDateTime.now())) {
            throw new OutOfDateException();
        }

        article.incrementViewsAsync();

        return article;
    }

    @Transactional(readOnly = true)
    public Page<Article> getPage(Pageable pageable) {
        return articleRepository.findAllValid(LocalDateTime.now(), pageable);
    }

    @Transactional(readOnly = true)
    public Page<Article> getPageEvery(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }


    public void saveWithAttach(Long articleId, List<MultipartFile> attachFiles) {
        Article article = articleRepository
                .findById(articleId)
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
    public void incrementViewCount(Long articleId, long increment) {
        Article article = articleRepository
                .findById(articleId)
                .orElseThrow(EntityNotFoundException::new);
        article.incrementViewsSync(increment);
    }
}
