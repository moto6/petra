package com.jari.jari.comment.service;

import com.jari.jari.account.entity.Account;
import com.jari.jari.article.entity.Article;
import com.jari.jari.article.service.ArticleService;
import com.jari.jari.comment.entity.Comment;
import com.jari.jari.comment.repository.CommentRepository;
import com.jari.jari.exception.exceptions.CommentDeleteUnauthorized;
import com.jari.jari.exception.exceptions.GuestForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static com.jari.jari.common.auth.UnknownAccount.guestAuth;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final ArticleService articleService;

    @Override
    public Comment create(Long articleId, Account account, Comment comment) {

        if (account == guestAuth()) {
            throw new GuestForbiddenException();
        }
        comment.setRelation(articleService.read(articleId), account);
        return commentRepository.save(comment);
    }

    @Override
    public void delete(Long articleId, Long commentId, Account account) {

        Article article = articleService.read(articleId);
        Comment comment = commentRepository.findByIdAndArticle(commentId, article).orElseThrow(EntityNotFoundException::new);

        if (comment.getAccount().getId().equals(account.getId())) {
            commentRepository.delete(comment);
        }
        throw new CommentDeleteUnauthorized();
    }

    @Override
    public Comment read(Long commentId) {

        return commentRepository.findById(commentId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<Comment> readCommentsList(Long articleId, Pageable pageable) {

        return commentRepository.findAllByArticleId(articleId, pageable);
    }
}
