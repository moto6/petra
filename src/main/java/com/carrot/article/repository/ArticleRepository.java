package com.carrot.article.repository;

import com.carrot.article.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ArticleRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

    @Query(value = "select p from Post p WHERE p.validUntil>:time and p.validFrom<:time")
    Page<Post> findAllValid(@Param("time") LocalDateTime time, Pageable pageable);

}
