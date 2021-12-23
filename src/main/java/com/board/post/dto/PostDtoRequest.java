package com.board.post.dto;

import com.board.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDtoRequest {

    @Autowired
    public PostDtoRequest(ModelMapper mapper) {
        this.mapper = mapper;
    }

    String title;

    String contents;

    LocalDateTime validFrom;

    LocalDateTime validUntil;

    @JsonIgnore
    private ModelMapper mapper;

    public Post toPost() {
        return mapper.map(this, Post.class);
    }

    //@todo : 첨부파일 여러개 필요
}
