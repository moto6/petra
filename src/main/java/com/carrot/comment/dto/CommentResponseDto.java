package com.carrot.comment.dto;

import com.carrot.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String contents;
    private String nickname;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.nickname = comment.getAccount().getNickname();
    }

    public static Page<CommentResponseDto> commentListResponse(Page<Comment> commentList) {
        List<CommentResponseDto> responseDtoList = commentList.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        return new PageImpl<>(responseDtoList, commentList.getPageable(), commentList.getTotalPages());
    }
}
