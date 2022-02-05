package com.carrot.post.repository;

import com.carrot.post.entity.RedisPost;
import org.springframework.data.repository.CrudRepository;

public interface RedisPostRepository extends CrudRepository<RedisPost, Long> {
}
