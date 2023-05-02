package com.slicequeue.jamcar.common.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class BaseRuntimeException extends RuntimeException {

    private final HttpStatus code;
    private final String message;
    private final Map<String, Object> detail;

    public BaseRuntimeException(HttpStatus code, String message, Map<String, Object> detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }
}
