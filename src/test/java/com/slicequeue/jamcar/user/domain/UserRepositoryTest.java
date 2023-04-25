package com.slicequeue.jamcar.user.domain;

import com.slicequeue.jamcar.user.infra.JpaUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
@AutoConfigurationPackage
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(classes = {UserRepository.class, JpaUserRepository.class})
class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("01. save 성공 - case1 정상")
    void save_success_case1() {
        // given
        final User sampleUser = UserTest.getSampleUser(null);

        // when
        User result = userRepository.save(sampleUser);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserUid()).isNotNull();
        assertThat(result.getEmail()).isEqualTo(sampleUser.getEmail());
        assertThat(result.getPassword()).isEqualTo(sampleUser.getPassword());
        assertThat(result.getCreatedAt()).isNotNull();
        assertThat(result.getUpdatedAt()).isNotNull();

    }

    @Test
    @DisplayName("02. findByUid 성공 - case1 정상")
    void findByUid_success_case1() {
        // given
        final User sampleUser = UserTest.getSampleUser(null);
        testEntityManager.persistAndFlush(sampleUser);
        final UserUid uid = sampleUser.getUserUid();
        testEntityManager.detach(sampleUser);

        // when
        Optional<User> byId = userRepository.findByUid(uid);

        // then
        assertThat(byId.isPresent()).isTrue();
        User result = byId.get();
        assertThat(result.getUserUid()).isEqualTo(uid);
        assertThat(result.getEmail()).isEqualTo(sampleUser.getEmail());
        assertThat(result.getPassword()).isEqualTo(sampleUser.getPassword());
        assertThat(result.getName()).isEqualTo(sampleUser.getName());
        assertThat(result.getCreatedAt()).isNotNull();
        assertThat(result.getUpdatedAt()).isNotNull();

    }

}
