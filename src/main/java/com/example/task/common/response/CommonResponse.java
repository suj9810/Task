package com.example.task.common.response;

import com.example.task.common.code.ResponseCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    private final HttpStatus httpStatus;
    private String status;
    private String message;
    private T data;

    public static <T> CommonResponse<T> of(ResponseCode responseCode, T data) {
        return CommonResponse.<T>builder()
                .status(String.valueOf(responseCode.getStatus()))
                .message(responseCode.getMessage())
                .build();
    }

    public static <T> CommonResponse<T> from(ResponseCode responseCode) {
        return CommonResponse.<T>builder()
                .status(responseCode.getCode())
                .message(responseCode.getMessage())
                .build();
    }
}
