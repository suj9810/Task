package com.example.task.common.exception;

import com.example.task.common.code.ResponseCode;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private final ResponseCode responseCode;

    public CustomException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
        return this.responseCode;
    }

    public HttpStatus getHttpStatus() {
        return this.responseCode.getHttpStatus();
    }
}