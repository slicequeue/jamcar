package com.slicequeue.jamcar.user.command.application;

import com.slicequeue.jamcar.user.command.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserService {

    private final UserRepository userRepository;

    @Transactional
    public UserUid createUser(CreateUserRequest createUserRequest) {
        // 검증
        if (createUserRequest == null) throw new IllegalArgumentException();
        if (createUserRequest.getEmail() == null) throw new IllegalArgumentException();
        if (createUserRequest.getPassword() == null) throw new IllegalArgumentException();
        if (createUserRequest.getName() == null) throw new IllegalArgumentException();

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
