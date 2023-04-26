package com.carrot.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Schema(description = "응답 메세지 DTO")
@Getter
public class Respons {
    @Schema(description = "메세지", example = "message")
    private final String message;
    @Schema(description = "상태코드", example = "")
    private final String code;
    private final int statusCode;

    @Builder
    public Respons(String message, String code, int statusCode) {
        this.message = message;
        this.code = code;
        this.statusCode = statusCode;
    }

    public static Respons of(SuccessCode successCode) {
        return Respons.builder()
                .message(successCode.getMessage())
                .statusCode(successCode.getHttpStatus().value())
                .build();
    }

    public static Respons of(ErrorCode errorCode) {
        return Respons.builder()
                .code(errorCode.getCode().toString())
                .message(errorCode.getMessage())
                .statusCode(errorCode.getHttpStatus().value())
                .build();
    }

    public static ResponseEntity<Respons> toResponseEntity(SuccessCode successCode) {
        return ResponseEntity.status(successCode.getHttpStatus().value())
                .body(Respons.builder()
                        .message(successCode.getMessage())
                        .statusCode(successCode.getHttpStatus().value())
                        .build());
    }

    public static ResponseEntity<Respons> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus().value())
                .body(Respons.builder()
                        .code(errorCode.getCode().toString())
                        .message(errorCode.getMessage())
                        .statusCode(errorCode.getHttpStatus().value())
                        .build());
    }
}
