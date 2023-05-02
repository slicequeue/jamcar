package com.slicequeue.jamcar.common.exception;

import com.slicequeue.jamcar.common.base.BaseRuntimeException;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class BadRequestException extends BaseRuntimeException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message, null);
    }

    public BadRequestException(String message, Map<String, Object> detail) {
        super(HttpStatus.BAD_REQUEST, message, detail);
    }

    public BadRequestException(IllegalArgumentException e) {
        super(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }
}
