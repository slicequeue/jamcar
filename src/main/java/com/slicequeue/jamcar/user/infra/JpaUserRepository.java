package com.slicequeue.jamcar.user.infra;

import com.slicequeue.jamcar.user.command.domain.User;
import com.slicequeue.jamcar.user.command.domain.UserRepository;
import com.slicequeue.jamcar.user.command.domain.vo.Email;
import com.slicequeue.jamcar.user.command.domain.vo.UserUid;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

    @Override
    public Optional<User> findByEmail(Email email) {
        try {
            User result = (User) entityManager.createQuery("SELECT user from User user where user.email = ?1")
                    .setParameter(1, email)
                    .getSingleResult();
            return Optional.of(result);
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }
}
