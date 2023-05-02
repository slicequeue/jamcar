package com.slicequeue.jamcar.common.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Log4j2
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        log.error(ex);
        return ResponseEntity.status(ex.getCode()).body(
                        JamcarErrorResponse.builder()
                                .code(ex.getCode().value())
                                .message(ex.getMessage())
                                .detail(ex.getDetail())
                                .build()
                );
    }

}
