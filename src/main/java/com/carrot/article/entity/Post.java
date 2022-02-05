package com.carrot.article.entity;

import com.carrot.common.BaseEntity;
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

@Table(name = "post")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post extends BaseEntity {


    @Id
    @GeneratedValue
    @Column(name = "post_id")
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

    public Post update(Post post) {
        this.title = post.title;
        this.contents = post.contents;
        this.author = post.author;
        this.validUntil = post.validUntil;
        this.validFrom = post.validFrom;
        return this;
    }

    public void incrementViewsAsync() {
        //@todo : Redis 로 비동기적으로 업데이트 되도록 처리하는 코드를 작성해야 함
    }

    public void incrementViewsSync(long count) {
        this.viewCount += count;
    }

    public Post deepCopy() {
        return Post.builder()
                .id(this.getId())
                .title(this.getTitle())
                .contents(this.getContents())
                .viewCount(this.getViewCount())
                .validFrom(this.getValidFrom())
                .validUntil(this.getValidUntil())
                .build();
    }
}
