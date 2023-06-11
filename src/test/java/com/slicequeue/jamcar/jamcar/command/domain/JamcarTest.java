package com.slicequeue.jamcar.jamcar.command.domain;

import com.slicequeue.jamcar.jamcar.command.domain.vo.Address;
import com.slicequeue.jamcar.jamcar.command.domain.vo.Creator;
import com.slicequeue.jamcar.user.command.domain.User;
import com.slicequeue.jamcar.user.command.domain.UserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@DataJpaTest
@AutoConfigurationPackage(basePackages = "com.slicequeue.jamcar.*")
@ContextConfiguration(classes = {Jamcar.class, User.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
class JamcarTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    @DisplayName("01.Jamcar 삽입 테스트")
    void insert_success_case1() {
        // given
        final User sampleUser = UserTest.getSampleUser(null);
        final User creatorUser = testEntityManager.persistAndFlush(sampleUser);
        final Instant now = Instant.now();
        final Jamcar jamcar = getSampleJamcar(creatorUser, now, now.plus(1, ChronoUnit.DAYS));

        // when
        Jamcar result = testEntityManager.persistAndFlush(jamcar);

        // then
        Assertions.assertThat(result).isNotNull();

    }

    public static Jamcar getSampleJamcar(User creatorUser, Instant startedAt, Instant endedAt) {
        return Jamcar.newJamcar()
                .creator(Creator.newCreator().userUid(creatorUser.getUserUid()).name(creatorUser.getName()).build())
                .startDate(startedAt)
                .endDate(endedAt)
                .fromAddress(Address.newAddress().postalCode("05272").address("서울특별시 강동구 상암로 251 주공9단지 아파트 903동 1305호").build())
                .toAddress(Address.newAddress().postalCode("13529").address("경기 성남시 분당구 판교역로 166").build())
                .build();
    }

}
