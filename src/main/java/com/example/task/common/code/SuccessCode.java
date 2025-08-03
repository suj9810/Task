package com.example.task.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResponseCode{
    SUCCESS_USER_SIGNUP(HttpStatus.CREATED, "회원가입을 성공하였습니다." , "SUCCESS_USER_SIGNUP"),
    SUCCESS_USER_LOGIN(HttpStatus.OK, "로그인에 성공하였습니다.", "SUCCESS_USER_LOGIN"),
    SUCCESS_UPDATE_USER_ROLE(HttpStatus.OK, "권한 변경에 성공하였습니다.", "SUCCESS_UPDATE_USER_ROLE")

    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }
}
