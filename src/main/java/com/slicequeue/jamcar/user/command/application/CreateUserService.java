package com.slicequeue.jamcar.user.command.application;

import com.slicequeue.jamcar.user.command.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class CreateUserService {

    private final UserRepository userRepository;

    @Transactional
    public UserUid createUser(CreateUserRequest createUserRequest) {
        // 검증
        Assert.notNull(createUserRequest, "createUserRequest");
        Assert.notNull(createUserRequest.getEmail(), "email");
        Assert.notNull(createUserRequest.getPassword(), "password");
        Assert.notNull(createUserRequest.getName(), "name");

        // 엔티티 생성
        User user = User.newUser()
                .email(new Email(createUserRequest.getEmail()))
                .password(new Password(createUserRequest.getPassword()))
                .name(createUserRequest.getName())
                .build();

        // 리포지토리 반영, 리턴
        return userRepository.save(user).getUserUid();
    }

}