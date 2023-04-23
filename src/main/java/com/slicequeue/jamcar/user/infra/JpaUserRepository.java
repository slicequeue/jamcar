package com.slicequeue.jamcar.user.infra;

import com.slicequeue.jamcar.user.domain.User;
import com.slicequeue.jamcar.user.domain.UserRepository;
import com.slicequeue.jamcar.user.domain.UserUid;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public User save(User user) {
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }

    @Override
    public Optional<User> findByUid(UserUid uid) {
        return Optional.ofNullable(entityManager.find(User.class, uid));
    }
}
