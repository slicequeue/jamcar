package com.slicequeue.jamcar.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Getter @Setter
public class CrawlingData {

    private String duration;

    private String distance;

    private String route;

    private String etc;


    private LocalDateTime at;


    public static CrawlingData from(String duration, String distance, String route, String etc, LocalDateTime at) {
        return new CrawlingData(
                duration,
                distance,
                route,
                etc,
                at
        );
    }

}
