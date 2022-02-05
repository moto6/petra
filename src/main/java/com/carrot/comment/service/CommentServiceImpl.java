package com.carrot.comment.service;

import com.carrot.account.entity.Account;
import com.carrot.comment.entity.Comment;
import com.carrot.comment.repository.CommentRepository;
import com.carrot.exception.custom.CommentDeleteUnauthorized;
import com.carrot.exception.custom.GuestForbiddenException;
import com.carrot.article.entity.Post;
import com.carrot.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static com.carrot.auth.UnknownAccount.guestAuth;

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

        Post article = articleService.read(articleId);
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
