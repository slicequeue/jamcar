package com.slicequeue.jamcar.common.exception;

import com.slicequeue.jamcar.common.base.BaseRuntimeException;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Log4j2
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BaseRuntimeException.class)
    ResponseEntity<Object> handleBaseRuntimeException(BaseRuntimeException ex) {
        log.error(ex);
        return handleExceptionAndGetResponseEntity(ex);
    }

    @NotNull
    private static ResponseEntity<Object> handleExceptionAndGetResponseEntity(BaseRuntimeException ex) {
        return ResponseEntity.status(ex.getCode()).body(JamcarErrorResponse.from(ex));
    }

}
