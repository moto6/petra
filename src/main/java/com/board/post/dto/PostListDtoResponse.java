package com.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostListDtoResponse {
    private List<PostDtoResponse> list;
    private Pageable pageable;

    public PostListDtoResponse(List<PostDtoResponse> list, Pageable pageable) {
        this.list = list;
        this.pageable = pageable;
    }
}
