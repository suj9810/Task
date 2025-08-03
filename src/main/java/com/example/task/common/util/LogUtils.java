package com.example.task.common.util;

import com.example.task.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtils {
    public static void logError(Throwable throwable) {
        if (throwable instanceof BaseException exception) {
            log.error("예외 발생 : {} (ErrorCode : {})", exception.getMessage(), exception.getMessage());
        }
    }
}
