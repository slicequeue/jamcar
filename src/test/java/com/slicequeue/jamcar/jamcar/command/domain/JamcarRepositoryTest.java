package com.slicequeue.jamcar.jamcar.command.domain;

import com.slicequeue.jamcar.common.type.Status;
import com.slicequeue.jamcar.jamcar.command.domain.vo.Creator;
import com.slicequeue.jamcar.user.command.domain.User;
import com.slicequeue.jamcar.user.command.domain.UserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
@AutoConfigurationPackage(basePackages = "com.slicequeue.jamcar.*")
@ContextConfiguration(classes = {JamcarRepository.class/*, Jamcar.class*/})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JamcarRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private JamcarRepository jamcarRepository;

    @Test
    @DisplayName("01.save 성공 - case1 정상")
    void save_success_case1() {
        // given
        final Instant now = Instant.now();
        final User sampleUser = UserTest.getSampleUser(null);
        final User creatorUser = testEntityManager.persistAndFlush(sampleUser);
        Jamcar sampleJamcar = JamcarTest.getSampleJamcar(creatorUser, now, now.plus(7, ChronoUnit.DAYS));

        // when
        Jamcar result = jamcarRepository.save(sampleJamcar);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getCreator().getUserUid()).isEqualTo(sampleUser.getUserUid());
    }

    @Test
    @DisplayName("02.findByCreatorAndFromAddressAndToAddressAndStatusIn 성공 - case1 정상")
    void findByCreatorAndFromAddressAndToAddressAndStatusIn_success_case1() {
        // given
        final Instant now = Instant.now();
        final User sampleUser = UserTest.getSampleUser(null);
        final User creatorUser = testEntityManager.persistAndFlush(sampleUser);
        Jamcar sampleJamcar = JamcarTest.getSampleJamcar(creatorUser, now, now.plus(1, ChronoUnit.DAYS));
        jamcarRepository.save(sampleJamcar);

        testEntityManager.flush();

        Creator creator = Creator.newCreator()
                .userUid(creatorUser.getUserUid())
                .name(creatorUser.getName())
                .build();

        // when
        Optional<Jamcar> optional = jamcarRepository.findByCreatorAndFromAddressAndToAddressAndStatusInAndIsDeletedIsFalse(
                creator,
                sampleJamcar.getFromAddress(),
                sampleJamcar.getToAddress(),
                List.of(Status.TO_DO, Status.ON_PROGRESS)
        );

        // then
        assertThat(optional).isPresent();
    }

}
