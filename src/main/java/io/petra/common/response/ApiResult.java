package io.petra.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> {
    private int HttpStatus;
    private T data;


    //builder 에 의해 생성된 객체는 항상 ApiResponse<T> 타입이므로 올바른 형변환입니다. 따라서 비검사 경고를 제거했습니다
    @SuppressWarnings("unchecked")
    public static <T> ApiResult<T> success(T data, HttpStatus httpStatus) {

        return (ApiResult<T>) ApiResult.builder()
                .data(data)
                .HttpStatus(httpStatus.value())
                .build();
    }

    //위 success() 메서드 주석 내용과 동일합니다
    @SuppressWarnings("unchecked")
    public static <T> ApiResult<T> fail(HttpStatus httpStatus, String message) {

        return (ApiResult<T>) ApiResult.builder()
                .HttpStatus(httpStatus.value())
                .data(message)
                .build();
    }

    public ResponseEntity<?> responseBuild() {

        return ResponseEntity
                .status(this.HttpStatus)
                .body(data);
    }
}
