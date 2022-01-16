package com.board.post.service;

import com.board.config.ViewCountRedisHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostViewCountService {

    @Value("${viewCountLazyUpdatePeriodSecond}")
    Integer INTERVAL;

    //<Key:postId / Value:Time>
    private final RedisTemplate<Long, ViewCountRedisHandler> timer;


    private final PostServiceImpl postServiceImpl;

    public void intervalCount(Long postId) {

        if (isElapsed(postId, INTERVAL)) {
            applyCount(postId);
            return;
        }
        caching(postId);


    }

    private void caching(Long postId) {
        if(containsKey(postId)) {
            //값만 더하기
            timer.opsForValue().get(postId).increment();
            return;
        }

    }

    private boolean containsKey(Long postId) {
        ViewCountRedisHandler handler = timer.opsForValue().get(postId);
        if(handler == null) {
            return false;
        }
        return true;
    }

    @Transactional
    void applyCount(Long postId) {
        postServiceImpl.incrementViewCount(postId, getCount(postId));
        timer.opsForValue().decrement(postId);

    }

    public boolean isElapsed(Long postId, Integer interval) {
        LocalDateTime standardTime = timer
                .opsForValue()
                .get(postId)
                .getTime();
        LocalDateTime currentTime = LocalDateTime.now();
        return standardTime.plusSeconds(interval).isAfter(currentTime);

        //
        //timer.opsForValue().set(postId, LocalDateTime.now());
        //.//return false;
    }


    private long getCount(Long postId) {
        Long aLong = counter.opsForValue().get(postId);
        if (aLong != null) {
            return aLong.intValue();
        }
        return 1;
    }


}
