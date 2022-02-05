package com.carrot.article.dto;

import com.carrot.article.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ArticleDtoRequestTest {

    public static final ArticleDtoRequest request1 = ArticleDtoRequest
            .builder()
            .title("1_어린왕자")
            .contents("1_작은별에 착륙하다")
            .validFrom(LocalDateTime.now())
            .validUntil(LocalDateTime.now().plusYears(100))
            .build();

    public static final ArticleDtoRequest request2 = ArticleDtoRequest
            .builder()
            .title("2_해리포터 혼혈왕자")
            .contents("2_스네이프교수님")
            .validFrom(LocalDateTime.now().minusYears(100))
            .validUntil(LocalDateTime.now().minusYears(1))
            .build();

    public static final ArticleDtoRequest request3 = ArticleDtoRequest
            .builder()
            .title("3_스프링부트")
            .contents("3_스프링은 IOC 컨테이너가 Bean 을 DI 해주는 OOP 프레임워크 입니다")
            .validFrom(null)
            .validUntil(null)
            .build();

    public static final ArticleDtoRequest request4 = ArticleDtoRequest
            .builder()
            .title("4_JPA DDD AOC")
            .contents("4_JPA-JPA-JPA-JPA-JPA-JPA-JPA-JPA-")
            .validFrom(null)
            .validUntil(LocalDateTime.now())
            .build();


    @Test
    @DisplayName("영원히 유효한 글이 되도록 날짜를 수정한다")
    public void validExtension() {

        //given
        ArticleDtoRequest request = request3.deepCopy();
        LocalDateTime initValidFrom = request.getValidFrom();
        LocalDateTime initValidUntil = request.getValidUntil();

        //when
        request.validExtension();
        Post post = request.toPost();

        //then
        assertThat(request.getValidUntil()).isNotEqualTo(initValidUntil);
        assertThat(request.getValidFrom()).isNotEqualTo(initValidFrom);
        assertThat(post.isExpired(LocalDateTime.now())).isTrue();
        log.info("validExtension() 메서드 테스트");
    }
}
