package com.board.post.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostViewCountService {

    private static final Map<Long, ViewCount> redisSimulation = new HashMap<>();

    //<Key:postId / Value:Time>
    private final RedisTemplate<Long, ZonedDateTime> timer;

    //<Key:postId / Value:Count>
    private final RedisTemplate<Long, Integer> counter;

    private final PostService postService;

    public void intervalCount(Long postId) {
        //byRedis(postId); //레디스 Key expired 기능으로 구현하려고 했으나 실패
        byMillis(postId);
    }


    public void byRedis(Long postId) {
        if (!isContained(postId)) {
            postService.addViews(postId, getCount(postId));
            identifier.opsForValue().set(postId, postId, 1, TimeUnit.SECONDS);
            identifier.expire(postId, 1, TimeUnit.SECONDS);
            counter.opsForValue().set(postId, 1L);
            log.info("업데이트");
        } else {
            log.info("캐싱");
            counter.opsForValue().set(postId, getCount(postId) + 1L);
        }
    }


    private long getCount(Long postId) {
        Long aLong = counter.opsForValue().get(postId);
        if (aLong != null) {
            return aLong.intValue();
        }
        return 1;
    }

    private boolean isContained(Long postId) {
        Long sample = identifier.opsForValue().get(postId);
        return identifier.opsForValue().get(postId) != null;

    }

    @AllArgsConstructor
    @Getter
    public class ViewCount {
        private long millis;
        private long upCount;
    }

}
