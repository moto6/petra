package io.petra.article.repository;

import io.petra.article.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findAll(Pageable pageable);

    @Query(value = "select article from Article AS article WHERE article.validUntil>:time and article.validFrom<:time")
    Page<Article> findAllValid(@Param("time") LocalDateTime time, Pageable pageable);
}
