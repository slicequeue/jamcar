package com.slicequeue.jamcar.jamcar.command.domain;

import com.slicequeue.jamcar.common.exception.ConflictException;
import com.slicequeue.jamcar.jamcar.command.application.CreateJamcarRequest;
import com.slicequeue.jamcar.jamcar.command.domain.vo.Address;
import com.slicequeue.jamcar.jamcar.command.domain.vo.Creator;
import com.slicequeue.jamcar.user.command.domain.User;
import com.slicequeue.jamcar.user.command.domain.UserTest;
import com.slicequeue.jamcar.user.command.domain.vo.UserUid;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ContextConfiguration;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
@ContextConfiguration(classes = {CreateJamcarService.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CreateJamcarServiceTest {

    @InjectMocks
    CreateJamcarService createJamcarService;

    @Mock
    JamcarRepository jamcarRepository;

    @Test
    @DisplayName("1.create 실패 - case1 생성 정책 위반")
    void create_fail_case1() {
        // given
        final User user = UserTest.getSampleUser(new UserUid());
        final Instant now = Instant.now();
        final Jamcar sampleJamcar = JamcarTest.getSampleJamcar(user, now.minus(7, ChronoUnit.DAYS), now);
        final CreateJamcarRequest request = CreateJamcarRequest.builder()
                .toPostalCode(sampleJamcar.getToAddress().getPostalCode())
                .toAddress(sampleJamcar.getToAddress().getAddress())
                .fromPostalCode(sampleJamcar.getFromAddress().getPostalCode())
                .fromAddress(sampleJamcar.getFromAddress().getAddress())
                .build();
        final Creator creator = Creator.newCreator()
                .userUid(user.getUserUid())
                .name(user.getName())
                .build();

        when(jamcarRepository.findByCreatorAndFromAddressAndToAddressAndStatusInAndIsDeletedIsFalse(
                any(Creator.class), any(Address.class), any(Address.class), anyList()))
                .thenReturn(Optional.of(sampleJamcar));

        // when & then
        Assertions.assertThatThrownBy(() -> {
            createJamcarService.create(request, creator);
        }).isInstanceOf(ConflictException.class)
                .hasMessageContaining("생성 정책 위반");

    }

}
