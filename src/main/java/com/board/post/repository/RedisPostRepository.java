package com.board.post.repository;

import com.board.post.entity.RedisPost;
import org.springframework.data.repository.CrudRepository;

public interface RedisPostRepository extends CrudRepository<RedisPost, Long> {
}
