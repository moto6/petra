package com.jari.jari.comment.dto;

import com.jari.jari.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentRequestDto {

    private String contents;

    public Comment createComment() {
        return Comment.builder()
                .contents(contents)
                .build();
    }
}
