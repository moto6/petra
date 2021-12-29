package com.board.config;

import com.board.post.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Slf4j
@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.redis.port}")
    private int REDIS_PORT;

    @Value("${spring.redis.host}")
    String REDIS_HOST;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(REDIS_HOST, REDIS_PORT);
    }


    @Bean
    public RedisTemplate<Long, Integer> redisTemplateA() {
        RedisTemplate<Long, Integer> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    @Bean
    public RedisTemplate<Long, String> redisTemplateC() {
        RedisTemplate<Long, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    @Bean
    public RedisTemplate<Long, Long> redisTemplateB() {
        RedisTemplate<Long, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}
