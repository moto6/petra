package com.board.post.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostViewCountService {

    //<postId,increment>
    private final RedisTemplate<Long, Long> counter;

    //<postId,postId>
    private final RedisTemplate<Long, Long> identifier;

    private final PostService postService;

    private static final Map<Long, ViewCount> redisSimulation = new HashMap<>();

    public void intervalCount(Long postId) {
        //byRedis(postId); //레디스 Key expired 기능으로 구현하려고 했으나 실패
        byMillis(postId);
    }

    private void byMillis(Long postId) {
        if (redisSimulation.containsKey(postId)) {
            ViewCount firstSignal = redisSimulation.get(postId);
            if( (System.currentTimeMillis() - firstSignal.getMillis()) > 1000) { //1초이상 지난경우
                postService.addViews(postId,firstSignal.getUpCount());
                redisSimulation.remove(postId);//숫자를 더하고 삭제
            }
            else {
                redisSimulation.replace(postId, new ViewCount(firstSignal.getMillis(), firstSignal.getUpCount()+1));
                // 숫자만 더하고 시간은 유지
            }
        }else {
            redisSimulation.put(postId, new ViewCount(System.currentTimeMillis(),1));
        }
    }

    public void byRedis(Long postId) {
        if (!isContained(postId)) {
            postService.addViews(postId, getCount(postId));
            identifier.opsForValue().set(postId, postId, 1, TimeUnit.SECONDS);
            identifier.expire(postId, 1, TimeUnit.SECONDS);
            counter.opsForValue().set(postId, 1L);
            log.info("업데이트");
        }else {
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
