package com.example.task.common.exception;

import com.example.task.common.code.ResponseCode;
import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {
    public abstract ResponseCode getResponseCode();

    public abstract HttpStatus getHttpStatus();

}
