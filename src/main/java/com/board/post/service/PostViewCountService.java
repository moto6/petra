package com.board.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PostViewCountService {

    private final RedisTemplate<Long, Integer> counter;

    private final RedisTemplate<Long, Long> identifier;

    private final PostService postService;

    public void intervalCount(Long postId) {
        if (!isContained(postId)) {
            postService.addViews(postId, getCount(postId));
            identifier.opsForValue().set(postId, postId, 1, TimeUnit.SECONDS);
            getCount(postId);
            counter.opsForValue().set(postId, 1);
            return;
        }
        counter.opsForValue().set(postId, getCount(postId));
    }

    private int getCount(Long postId) {
        Integer i = counter.opsForValue().get(postId);
        if (i != null) {
            return i;
        }
        throw new AssertionError();
    }

    private boolean isContained(Long postId) {
        return identifier.opsForValue().get(postId) == null;
    }
}
