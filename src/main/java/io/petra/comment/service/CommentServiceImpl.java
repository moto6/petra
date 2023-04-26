package io.petra.comment.service;

import io.petra.account.domain.Account;
import io.petra.comment.domain.Comment;
import io.petra.comment.repository.CommentRepository;
import io.petra.comment.exception.CommentDeleteUnauthorized;
import io.petra.account.exception.GuestForbiddenException;
import io.petra.article.domain.Article;
import io.petra.article.service.ArticleService;
import io.petra.auth.UnknownAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final ArticleService articleService;

    @Override
    public Comment create(Long articleId, Account account, Comment comment) {

        if (account == UnknownAccount.guestAuth()) {
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
