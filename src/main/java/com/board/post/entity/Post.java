package com.board.post.entity;

import com.board.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    Long id;

    String title;

    String contents;

    String author;

    Long viewCount;

    LocalDateTime validFrom;

    LocalDateTime validUntil;

    public boolean isValidPeriod(LocalDateTime standardTime) {
        return validFrom.isAfter(standardTime) && validUntil.isBefore(standardTime);
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
}
