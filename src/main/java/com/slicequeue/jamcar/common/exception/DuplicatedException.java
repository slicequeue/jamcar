package com.slicequeue.jamcar.common.exception;

import com.slicequeue.jamcar.common.base.BaseRuntimeException;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class DuplicatedException extends BaseRuntimeException {
    public DuplicatedException(String message, Map<String, Object> detail) {
        super(HttpStatus.CONFLICT, message, detail);
    }

    public DuplicatedException(String message) {
        super(HttpStatus.CONFLICT, message, null);
    }
}
