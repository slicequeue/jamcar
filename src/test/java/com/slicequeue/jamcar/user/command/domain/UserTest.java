package com.slicequeue.jamcar.user.command.domain;

import com.slicequeue.jamcar.common.utils.ReflectUtil;
import com.slicequeue.jamcar.user.command.domain.Email;
import com.slicequeue.jamcar.user.command.domain.Password;
import com.slicequeue.jamcar.user.command.domain.User;
import com.slicequeue.jamcar.user.command.domain.UserUid;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigurationPackage
@ContextConfiguration(classes = {User.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    @DisplayName("01.삽입 성공 - case1 정상")
    void insert_success_case1() {
        // given
        final Email email = new Email("slicequeue@gmail.com");
        final Password password = new Password("test123@");
        final String name = "김진황";
        User user = User.newUser()
                .email(email)
                .password(password)
                .name(name)
                .build();

        // when
        User result = testEntityManager.persistAndFlush(user);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserUid()).isNotNull();
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getPassword()).isEqualTo(password);
    }

    public static User getSampleUser(UserUid uid) {
        final Email email = new Email("slicequeue@gmail.com");
        final Password password = new Password("test123@");
        final String name = "김진황";
        User user = User.newUser()
                .email(email)
                .password(password)
                .name(name)
                .build();

        if (uid != null) {
            try {
                ReflectUtil.setField(user, "userUid", uid);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
        return user;
    }

}