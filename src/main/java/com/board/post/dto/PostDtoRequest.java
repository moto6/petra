package com.board.post.dto;

import java.time.LocalDateTime;

public class PostDtoRequest {

    String title;

    String contents;

    LocalDateTime validFrom;

    LocalDateTime validUntil;

    //author는 account정보에서 자동으로 읽어오기

    //@todo 첨부파일 여러개 필요
}
