package com.slicequeue.jamcar.jamcar.presentation;

import com.slicequeue.jamcar.common.type.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class JamcarDto {

    private Long id;

    private String fromPostalCode;

    private String fromAddress;

    private String toPostalCode;

    private String toAddress;

    private String creatorName;

    private Instant startDate;

    private Instant endDate;

    private Status status;

}
