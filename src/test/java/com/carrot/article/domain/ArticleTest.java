package com.carrot.article.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ArticleTest {

    public static final Article ARTICLE_1 = Article
            .builder()
            .id(1L)
            .title("1_어린왕자")
            .contents("1_작은별에 착륙하다")
            .viewCount(999)
            .validFrom(LocalDateTime.now().minusYears(100))
            .validUntil(LocalDateTime.now().plusYears(100))
            .build();

    public static final Article ARTICLE_2 = Article
            .builder()
            .id(2L)
            .title("2_해리포터 혼혈왕자")
            .contents("2_스네이프교수님")
            .viewCount(777)
            .validFrom(LocalDateTime.now().minusYears(100))
            .validUntil(LocalDateTime.now().minusYears(50))
            .build();

    @Test
    @DisplayName("시간이 유효한지 아닌지를 판단한다")
    public void validPeriod() {
        //given
        //when
        //then
        assertThat(ARTICLE_1.isExpired(LocalDateTime.now())).isTrue();
        log.info("유효함 : 시작일:{}, 종료일:{}", ARTICLE_1.getValidFrom(), ARTICLE_1.getValidUntil());
        assertThat(ARTICLE_2.isExpired(LocalDateTime.now())).isFalse();
        log.info("유효하지 않음 : 시작일:{}, 종료일:{}", ARTICLE_2.getValidFrom(), ARTICLE_2.getValidUntil());
    }

    @Test
    @DisplayName("조회수가 증가한다")
    public void incrementViews() {
        //given
        Article article = ARTICLE_1;
        long originalViews = ARTICLE_1.getViewCount();
        long increase = 100L;

        //when
        article.incrementViewsSync(increase);

        //then
        assertThat(article.getViewCount()).isEqualTo(increase + originalViews);
    }
}
