package com.slicequeue.jamcar.jamcar.command.application;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class CreateJamcarRequest {

    @NotBlank(message = "fromAddress")
    private String fromAddress;

    @NotBlank(message = "toAddress")
    private String toAddress;

}
