package com.slicequeue.jamcar.user.command.application;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.Instant;

@Getter
public class LoginUserResponse {

    private final String accessToken;

    private final Instant expiredAt;

    @Builder
    public LoginUserResponse(String accessToken, Instant expiredAt) {
        this.accessToken = accessToken;
        this.expiredAt = expiredAt;
    }
}
