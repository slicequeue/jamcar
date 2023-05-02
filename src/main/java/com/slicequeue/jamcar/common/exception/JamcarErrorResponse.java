package com.slicequeue.jamcar.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@Getter
@JsonInclude(Include.NON_NULL)
public class JamcarErrorResponse {

    private Integer code;

    private String message;

    private Map<String, Object> detail;

    private JamcarErrorResponse() {}

    @Builder
    public JamcarErrorResponse(Integer code, String message, Map<String, Object> detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

}
