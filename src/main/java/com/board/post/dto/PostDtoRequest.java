package com.board.post.dto;

import com.board.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class PostDtoRequest {

    @Builder
    public PostDtoRequest(String title, String contents, LocalDateTime validFrom, LocalDateTime validUntil) {
        this.title = title;
        this.contents = contents;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
    }

    String title;

    String contents;

    LocalDateTime validFrom;

    LocalDateTime validUntil;

    @JsonIgnore
    private ModelMapper mapper = new ModelMapper();

    public Post toPost() {
        return mapper.map(this, Post.class);
    }

    //@todo : 첨부파일 여러개 필요
}
