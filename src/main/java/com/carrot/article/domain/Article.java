package com.carrot.article.domain;

import com.carrot.common.entityaudit.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "table_article")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article extends BaseEntity {


    @Id
    @GeneratedValue
    @Column(name = "article_id")
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    @Column(name = "author", length = 15)
    private String author;

    @Builder.Default
    @Column(name = "view_count", nullable = false)
    private long viewCount = 0;

    //@todo : audit 적용하도록 수정
    @Column(name = "valid_from", nullable = false)
    @DateTimeFormat(pattern = DATETIME_FORMAT)
    private LocalDateTime validFrom;

    @Column(name = "valid_until", nullable = false)
    @DateTimeFormat(pattern = DATETIME_FORMAT)
    private LocalDateTime validUntil;

    //@Todo : 첨부파일 추가필요

    public boolean isExpired(LocalDateTime standardTime) {
        return (validFrom.isBefore(standardTime) && validUntil.isAfter(standardTime));
    }

    public void config(String author) {
        this.author = author;
        this.viewCount = 0L;
    }

    public Article update(Article article) {
        this.title = article.title;
        this.contents = article.contents;
        this.author = article.author;
        this.validUntil = article.validUntil;
        this.validFrom = article.validFrom;
        return this;
    }

    public void incrementViewsAsync() {
        //@todo : Redis 로 비동기적으로 업데이트 되도록 처리하는 코드를 작성해야 함
    }

    public void incrementViewsSync(long count) {
        this.viewCount += count;
    }

    public Article deepCopy() {
        return Article.builder()
                .id(this.getId())
                .title(this.getTitle())
                .contents(this.getContents())
                .viewCount(this.getViewCount())
                .validFrom(this.getValidFrom())
                .validUntil(this.getValidUntil())
                .build();
    }
}
