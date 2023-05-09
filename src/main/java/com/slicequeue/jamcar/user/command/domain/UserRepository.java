package com.slicequeue.jamcar.user.command.domain;

import com.slicequeue.jamcar.user.command.domain.vo.Email;
import com.slicequeue.jamcar.user.command.domain.vo.UserUid;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository {

    @Transactional
    User save(User user);

    @Transactional(readOnly = true)
    Optional<User> findByUid(UserUid uid);

    @Transactional
    Optional<User> findByEmail(Email email);

}
