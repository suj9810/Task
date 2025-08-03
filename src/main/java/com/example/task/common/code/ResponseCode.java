package com.example.task.common.code;

import org.springframework.http.HttpStatus;

public interface ResponseCode {
    HttpStatus getHttpStatus();

    String getMessage();

    String getCode();
}
