package com.slicequeue.jamcar.user.command.application;

import com.slicequeue.jamcar.user.command.domain.UserUid;
import lombok.Getter;

@Getter
public class CreateUserResponse {

    private final String uid;

    private CreateUserResponse(String uid) {
        this.uid = uid;
    }

    public static CreateUserResponse from(UserUid userUid) {
        return new CreateUserResponse(userUid.toString());
    }

}
