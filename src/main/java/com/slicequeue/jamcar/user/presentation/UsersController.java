package com.slicequeue.jamcar.user.presentation;

import com.slicequeue.jamcar.common.exception.BadRequestException;
import com.slicequeue.jamcar.user.command.application.*;
import com.slicequeue.jamcar.user.command.domain.vo.UserUid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UsersController {

    private final CreateUserService createUserService;

    private final LoginUserService loginUserService;


    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        try {
            UserUid userUid = createUserService.createUser(createUserRequest);
            return ResponseEntity.ok(CreateUserResponse.from(userUid));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginUserResponse> loginUser(@RequestBody LoginUserRequest loginUserRequest) {
        try {
            LoginUserResponse response = loginUserService.login(loginUserRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }
    }

}
