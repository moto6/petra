package com.carrot.article.dto;

import com.carrot.article.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ArticleDtoRequest {

    @NotNull(message = "제목은 필수입니다")
    @Max(value = 50, message = "최대 길이는 50자 입니다")
    @Min(value = 1, message = "최소길이는 1자 입니다")
    private String title;

    @NotNull(message = "본문은 필수입니다")
    @Max(value = 500, message = "최대 길이는 500자 입니다")
    @Min(value = 1, message = "최소길이는 1자 입니다")
    private String contents;

    private LocalDateTime validFrom;

    private LocalDateTime validUntil;

    @Builder
    public ArticleDtoRequest(String title, String contents, LocalDateTime validFrom, LocalDateTime validUntil) {
        this.title = title;
        this.contents = contents;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
    }

    public Post toPost() {
        return Post
                .builder()
                .title(this.title)
                .contents(this.contents)
                .validFrom(this.validFrom == null ? LocalDateTime.now() : this.validFrom)
                .validUntil(this.validUntil == null ? LocalDateTime.now().plusYears(100) : this.validUntil)
                .build();
        //날짜를 입력하지 않았을 경우 기본 정책을 따름
    }

    public ArticleDtoRequest validExtension() {
        this.validFrom = LocalDateTime.now().minusYears(100);
        this.validUntil = LocalDateTime.now().plusYears(100);
        return this;
    }

    public ArticleDtoRequest deepCopy() {
        return ArticleDtoRequest
                .builder()
                .title(this.title)
                .contents(this.contents)
                .validFrom(this.validFrom)
                .validUntil(this.validUntil)
                .build();
    }
}
