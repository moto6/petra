package com.board.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int HttpStatus;
    private T data;
    private String message;

    //builder 에 의해 생성된 객체는 항상 ApiResponse<T> 타입이므로 올바른 형변환입니다. 따라서 비검사 경고를 제거했습니다
    @SuppressWarnings("unchecked")
    public static <T> ApiResponse<T> sussess(T data, HttpStatus httpStatus) {
        return (ApiResponse<T>) ApiResponse.builder()
                .data(data)
                .HttpStatus(httpStatus.value())
                .build();
    }

    //위 sussess() 메서드 주석 내용과 동일합니다
    @SuppressWarnings("unchecked")
    public static <T> ApiResponse<T> fail(HttpStatus httpStatus, String message) {
        return (ApiResponse<T>) ApiResponse.builder()
                .HttpStatus(httpStatus.value())
                .message(message)
                .build();
    }
}
