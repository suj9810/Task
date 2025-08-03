package com.example.task.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ResponseCode {
    // user
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 가입된 사용자입니다.", "USER_ALREADY_EXISTS"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다.", "USER_NOT_FOUND"),
    INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호가 유효하지 않습니다.", "INVALID_PASSWORD"),

    // auth
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증 정보가 유효하지 않습니다.", "UNAUTHORIZED"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다.", "FORBIDDEN"),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다.", "INVALID_TOKEN"),
    TOKEN_MISSING(HttpStatus.BAD_REQUEST, "존재하는 토큰이 없습니다.", "TOKEN_MISSING"),
    TOKEN_USER_MISMATCH(HttpStatus.BAD_REQUEST, "토큰과 유저가 일치하지 않습니다.", "TOKEN_USER_MISMATCH")
    ;


    private final HttpStatus httpStatus;
    private final String message;
    private final String code;

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }
}
