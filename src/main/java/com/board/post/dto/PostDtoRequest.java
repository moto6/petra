package com.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDtoRequest {
    String title;

    String contents;

    LocalDateTime validFrom;

    LocalDateTime validUntil;

    //@todo : author는 account정보에서 자동으로 읽어오기, DTO에서 안받고

    //@todo : 첨부파일 여러개 필요
}
