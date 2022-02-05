package com.carrot.article.repository;

import com.carrot.article.entity.RedisArticle;
import org.springframework.data.repository.CrudRepository;

public interface RedisArticleRepository extends CrudRepository<RedisArticle, Long> {
}
