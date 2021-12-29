package com.board.post.service;

import com.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PostViewCountService {

    private final RedisTemplate<Long, Integer> redisTemplate;

    private final PostService postService;

    public void intervalCount(Long postId) {
        if (!isContained(postId)) {
            postService.addViews(postId, 1);//todo : 미완성
            redisTemplate.opsForValue().set(postId, 1, 1, TimeUnit.SECONDS);
            redisTemplate.afterPropertiesSet();
            return;
        }
        Integer i = redisTemplate.opsForValue().get(postId);
    }

    private boolean isContained(Long postId) {
        return redisTemplate.opsForValue().get(postId) == null;
    }


}
