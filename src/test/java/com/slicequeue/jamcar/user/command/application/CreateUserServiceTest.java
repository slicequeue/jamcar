package com.slicequeue.jamcar.user.command.application;

import com.slicequeue.jamcar.user.command.domain.User;
import com.slicequeue.jamcar.user.command.domain.UserRepository;
import com.slicequeue.jamcar.user.command.domain.UserTest;
import com.slicequeue.jamcar.user.command.domain.vo.UserUid;
import com.slicequeue.jamcar.util.ParameterizedTestUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ContextConfiguration;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
@ContextConfiguration(classes = {CreateUserService.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CreateUserServiceTest {

    @InjectMocks
    CreateUserService createUserService;

    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("01.createUser 성공 - case1 정상")
    void createUser_success_case1() {
        // given
        final User sampleUser = UserTest.getSampleUser(new UserUid());
        final String email = sampleUser.getEmail().toString();
        final String password = sampleUser.getPassword().toString();
        final String name = sampleUser.getName();
        final CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(email);
        createUserRequest.setPassword(password);
        createUserRequest.setName(name);

        when(userRepository.save(any(User.class)))
                .thenReturn(sampleUser);

        // when
        UserUid result = createUserService.createUser(createUserRequest);

        // then
        assertThat(result).isEqualTo(sampleUser.getUserUid());

    }

    @ParameterizedTest
    @MethodSource("getArguments_createUser_fail_case1")
    @DisplayName("01.createUser 실패 - case1 인자불량")
    void createUser_fail_case1(String caseMsg, String email, String password, String name) {
        // given
        final CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(email);
        createUserRequest.setPassword(password);
        createUserRequest.setName(name);

        // when & then
        Assertions.assertThatThrownBy(() -> {
            createUserService.createUser(createUserRequest);
        }).isInstanceOf(IllegalArgumentException.class);

    }

    static Stream<Arguments> getArguments_createUser_fail_case1() {
        final User sampleUser = UserTest.getSampleUser(new UserUid());
        final String email = sampleUser.getEmail().toString();
        final String password = sampleUser.getPassword().toString();
        final String name = sampleUser.getName();
        return ParameterizedTestUtil.getParamStreamArguments(true, email, password, name);
    }


}
