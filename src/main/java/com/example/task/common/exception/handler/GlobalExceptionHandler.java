package com.example.task.common.exception.handler;

import com.example.task.common.exception.BaseException;
import com.example.task.common.exception.dto.ValidationError;
import com.example.task.common.response.CommonResponse;
import com.example.task.common.response.CommonResponses;
import com.example.task.common.code.ResponseCode;
import com.example.task.common.util.LogUtils;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<CommonResponse<ResponseCode>> handleBaseException(BaseException baseException) {
        LogUtils.logError(baseException);

        return ResponseEntity.status(baseException.getHttpStatus())
                .body(CommonResponse.from(baseException.getResponseCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponses<ValidationError>> inputValidationExceptionHandler(BindingResult result) {
        log.error(result.getFieldErrors().toString());

        List<ValidationError> validationErrors = result.getFieldErrors()
                .stream()
                .map(fieldError -> ValidationError.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .code(fieldError.getCode())
                        .build())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonResponses.of(HttpStatus.BAD_REQUEST.name(), "잘못된 요청입니다.", validationErrors));
    }
}
