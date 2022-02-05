package com.carrot.article.entity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@ToString
@RedisHash("Post")
public class RedisArticle {

    @Id
    private Long id;

    private int viewCount = 0;

    @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime RequestedAt;


    public void increaseViewCount(int viewCount) {
        this.viewCount++;
    }

    public boolean isElapsed(Long postId, Long interval) {
        return true;
    }

    /*
    public boolean isElapsed(Long postId, Integer interval) {
        LocalDateTime standardTime = timer
                .opsForValue()
                .get(postId)
                .getTime();
        LocalDateTime currentTime = LocalDateTime.now();
        return standardTime.plusSeconds(interval).isAfter(currentTime);

    }
     */
}
