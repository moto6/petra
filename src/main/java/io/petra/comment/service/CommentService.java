package io.petra.comment.service;

import io.petra.account.domain.Account;
import io.petra.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommentService {
    Comment create(Long articleId, Account account, Comment comment);

    void delete(Long articleId, Long commentId, Account account);

    Comment read(Long commentId);

    Page<Comment> readCommentsList(Long articleId, Pageable pageable);
}
