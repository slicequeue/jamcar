package com.slicequeue.jamcar.user.command.application;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

}
