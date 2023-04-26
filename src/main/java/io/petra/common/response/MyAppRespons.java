package io.petra.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Schema(description = "응답 메세지 DTO")
@Getter
public class MyAppRespons {
    @Schema(description = "메세지", example = "message")
    private final String message;
    @Schema(description = "상태코드", example = "")
    private final String code;
    private final int statusCode;

    @Builder
    public MyAppRespons(String message, String code, int statusCode) {
        this.message = message;
        this.code = code;
        this.statusCode = statusCode;
    }

    public static MyAppRespons of(SuccessCode successCode) {
        return MyAppRespons.builder()
                .message(successCode.getMessage())
                .statusCode(successCode.getHttpStatus().value())
                .build();
    }

    public static MyAppRespons of(ErrorCode errorCode) {
        return MyAppRespons.builder()
                .code(errorCode.getCode().toString())
                .message(errorCode.getMessage())
                .statusCode(errorCode.getHttpStatus().value())
                .build();
    }

    public static ResponseEntity<MyAppRespons> toResponseEntity(SuccessCode successCode) {
        return ResponseEntity.status(successCode.getHttpStatus().value())
                .body(MyAppRespons.builder()
                        .message(successCode.getMessage())
                        .statusCode(successCode.getHttpStatus().value())
                        .build());
    }

    public static ResponseEntity<MyAppRespons> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus().value())
                .body(MyAppRespons.builder()
                        .code(errorCode.getCode().toString())
                        .message(errorCode.getMessage())
                        .statusCode(errorCode.getHttpStatus().value())
                        .build());
    }
}
