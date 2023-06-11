package com.slicequeue.jamcar.common.exception;

import com.slicequeue.jamcar.common.base.BaseRuntimeException;
import org.springframework.http.HttpStatus;

public class ConflictException extends BaseRuntimeException {

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message, null);
    }

}
