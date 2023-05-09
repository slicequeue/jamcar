package com.slicequeue.jamcar.user.command.application;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class LoginUserRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @Builder
    public LoginUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
