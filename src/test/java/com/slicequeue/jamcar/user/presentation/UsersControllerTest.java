package com.slicequeue.jamcar.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slicequeue.jamcar.user.command.application.CreateUserRequest;
import com.slicequeue.jamcar.user.command.application.CreateUserResponse;
import com.slicequeue.jamcar.user.command.application.CreateUserService;
import com.slicequeue.jamcar.user.command.domain.User;
import com.slicequeue.jamcar.user.command.domain.UserTest;
import com.slicequeue.jamcar.user.command.domain.UserUid;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsersController.class)
@ContextConfiguration(classes = {UsersController.class})
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
}