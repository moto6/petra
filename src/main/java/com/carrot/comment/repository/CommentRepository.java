package com.carrot.comment.repository;

import com.carrot.article.domain.Article;
import com.carrot.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndArticle(Long commentId, Article article);

    Page<Comment> findAllByArticleId(Long articleId, Pageable pageable);
}
