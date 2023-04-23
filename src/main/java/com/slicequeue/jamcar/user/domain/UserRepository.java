package com.slicequeue.jamcar.user.domain;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository {

    @Transactional
    User save(User user);

    @Transactional(readOnly = true)
    Optional<User> findByUid(UserUid uid);

}
