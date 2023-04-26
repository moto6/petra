package io.petra.common.response;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public enum ErrorCode {

    // 4xxx : Client Fault
    ACCESS_DENIED(BAD_REQUEST, 4011, "access denied"),

    DUPLICATE_EMAIL(BAD_REQUEST, 4001, "duplicate email"),
    UNREGISTER_USER(BAD_REQUEST, 4002, "unregister user"),
    NOT_SAME_EMAIL(BAD_REQUEST, 4003, "not same email"),
    SOCIAL_USER(BAD_REQUEST, 4004, "social user"),
    INVALID_CODE(BAD_REQUEST, 4005, "invalid code"),
    INVALID_INVITATION(BAD_REQUEST, 4006, "Invalid invitation"),
    NOT_SOCIAL_EMAIL(BAD_REQUEST, 4007, "not social email"),

    WRONG_WORKSPACE_ID(BAD_REQUEST, 4001, "wrong workspace id"),
    WRONG_USER(BAD_REQUEST, 4002, "wrong user"),

    ALREADY_INVITED(BAD_REQUEST, 4101, "already invited"),
    SAME_PERMISSION(BAD_REQUEST, 4102, "same role"),

    NULL_MULTIPART_FILE(BAD_REQUEST, 4101, "null multipart file"),
    RESIZING_FAILED(BAD_REQUEST, 4002, "resizing failed"),
    NOT_IMAGE(BAD_REQUEST, 4003, "not image"),

    STATUS_NOT_CHANGED(BAD_REQUEST, 4201, "같은 상태로는 변경할 수 없습니다."),
    STATUS_NOT_EXIST(BAD_REQUEST, 4202, "존재하지 않는 상태값 입니다"),
    UNDEFINED_REQUEST(BAD_REQUEST, 4203, "올바르지 않은 요청입니다"),

    // 401 UNAUTHORIZED
    INVALID_TOKEN(UNAUTHORIZED, 4301, "invalid token"),
    NULL_TOKEN(UNAUTHORIZED, 4402, "null token"),

    // 403 FORBIDDEN
    PERMISSION_DINED(FORBIDDEN, 4411, "forbidden"),

    // 500 Interal server error
    FAIL_CONVERT(INTERNAL_SERVER_ERROR, 5000, "convert failed");

    ErrorCode(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
