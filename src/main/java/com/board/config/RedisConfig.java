package com.board.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.time.ZonedDateTime;

@Slf4j
@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.redis.host}")
    String REDIS_HOST;

    @Value("${spring.redis.port}")
    private int REDIS_PORT;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(REDIS_HOST, REDIS_PORT);
    }

    @Bean
    public RedisTemplate<Long, ZonedDateTime> redisTemplateTimer() {
        log.debug("Redis Bean add" + System.lineSeparator() +
                          "\t KEY[{}: {}] " + System.lineSeparator() +
                          "\t VALUE[{}: {}]",
                  "Long", "Entity's PRIMARY-KEY",
                  "ZonedDateTime", "time of requested");
        RedisTemplate<Long, ZonedDateTime> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    @Bean
    public RedisTemplate<Long, Integer> redisTemplateCounter() {
        log.debug("Redis Bean add" + System.lineSeparator() +
                          "\t KEY[{}: {}] " + System.lineSeparator() +
                          "\t VALUE[{}: {}]",
                  "Long", "Entity's PRIMARY-KEY",
                  "Integer", "count of request");
        RedisTemplate<Long, Integer> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

}
