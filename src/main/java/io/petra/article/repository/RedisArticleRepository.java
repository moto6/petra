package io.petra.article.repository;

import io.petra.article.domain.RedisArticle;
import org.springframework.data.repository.CrudRepository;

public interface RedisArticleRepository extends CrudRepository<RedisArticle, Long> {
}
