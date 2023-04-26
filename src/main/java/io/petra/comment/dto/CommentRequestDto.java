package io.petra.comment.dto;

import io.petra.comment.domain.Comment;
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
