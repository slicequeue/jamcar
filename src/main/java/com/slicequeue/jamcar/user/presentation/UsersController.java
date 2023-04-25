package com.slicequeue.jamcar.user.presentation;

import com.slicequeue.jamcar.user.command.application.CreateUserRequest;
import com.slicequeue.jamcar.user.command.application.CreateUserResponse;
import com.slicequeue.jamcar.user.command.application.CreateUserService;
import com.slicequeue.jamcar.user.command.domain.UserUid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UsersController {

    private final CreateUserService createUserService;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        UserUid userUid = createUserService.createUser(createUserRequest);
        return ResponseEntity.ok(CreateUserResponse.from(userUid));
    }

}
