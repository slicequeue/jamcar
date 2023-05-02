package com.slicequeue.jamcar.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slicequeue.jamcar.common.exception.RestResponseExceptionHandler;
import com.slicequeue.jamcar.user.command.application.CreateUserRequest;
import com.slicequeue.jamcar.user.command.application.CreateUserService;
import com.slicequeue.jamcar.user.command.domain.User;
import com.slicequeue.jamcar.user.command.domain.UserTest;
import com.slicequeue.jamcar.user.command.domain.UserUid;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static com.slicequeue.jamcar.util.ParameterizedTestUtil.getParamStreamArguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
@ContextConfiguration(classes = {UsersController.class, RestResponseExceptionHandler.class})
@TestMethodOrder(MethodOrderer.DisplayName.class)
class UsersControllerTest {

    @MockBean
    private CreateUserService createUserService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("1. 사용자 생성 API 성공 - case1 정상")
    void createUser_case1() throws Exception {
        // given
        final String targetUrl = "/users/new";
        final User sampleUser = UserTest.getSampleUser(new UserUid());
        final CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(sampleUser.getEmail().toString());
        createUserRequest.setPassword(sampleUser.getPassword().toString());
        createUserRequest.setName(sampleUser.getName());

        when(createUserService.createUser(any(CreateUserRequest.class)))
                .thenReturn(sampleUser.getUserUid());

        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post(targetUrl)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest))
        );

        // then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uid").isString());
    }

    @ParameterizedTest
    @MethodSource("getArgumentsCreateUser_case2")
    @DisplayName("1. 사용자 생성 API 실패 - case1 인자 누락 케이스")
    void createUser_case2(String caseMsg, String email, String password, String name) throws Exception {
        // given
        final String targetUrl = "/users/new";
        final CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail(email);
        createUserRequest.setPassword(password);
        createUserRequest.setName(name);

        when(createUserService.createUser(any(CreateUserRequest.class)))
                .thenThrow(IllegalArgumentException.class);

        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post(targetUrl)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest))
        );

        // then
        result.andDo(print())
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> getArgumentsCreateUser_case2() {
        final User sampleUser = UserTest.getSampleUser(new UserUid());
        return getParamStreamArguments(true,
                sampleUser.getEmail().toString(), sampleUser.getPassword().toString(), sampleUser.getName());
    }
}
