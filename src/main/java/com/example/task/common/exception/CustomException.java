package com.example.task.common.exception;

import com.example.task.common.code.ResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final ResponseCode responseCode;

    public CustomException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public HttpStatus getHttpStatus() {
        return this.responseCode.getStatus();
    }
}