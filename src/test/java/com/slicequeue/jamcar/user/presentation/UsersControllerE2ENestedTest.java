package com.slicequeue.jamcar.user.presentation;

import com.slicequeue.jamcar.user.command.application.CreateUserRequest;
import com.slicequeue.jamcar.user.command.application.LoginUserRequest;
import com.slicequeue.jamcar.user.command.domain.User;
import com.slicequeue.jamcar.user.command.domain.UserTest;
import com.slicequeue.jamcar.user.command.domain.vo.UserUid;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.stream.Stream;

import static com.slicequeue.jamcar.util.ParameterizedTestUtil.getParamStreamArguments;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class UsersControllerE2ENestedTest {

    @Autowired
    TestEntityManager testEntityManager;

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        System.out.println("setUp1");
        RestAssured.port = port;
        System.out.println(RestAssured.port);
    }

    @Nested
    @Order(1)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("사용사 생성 테스트")
    class CreateUserTest {

        @Test
        @Order(1)
        @DisplayName("성공한다.")
        void createUser_success() {
            // given
            final String targetUrl = "/users/new";
            final User sampleUser = UserTest.getSampleUser(new UserUid());
            final CreateUserRequest createUserRequest = new CreateUserRequest();
            createUserRequest.setEmail(sampleUser.getEmail().toString());
            createUserRequest.setPassword(sampleUser.getPassword().toString());
            createUserRequest.setName(sampleUser.getName());

            // when
            ExtractableResponse<Response> response = RestAssured
                    /* given 절은 어떤 http request 를 보내줄지 지정 */
                    .given().log().all()
                    .body(createUserRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    /* when 절은 이제 어떠한 URI API 호출할 것인지 지정 */
                    .when()
                    .post(targetUrl)
                    /* then 절은 결과를 리턴 */
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.body().jsonPath().getUUID("uid")).isNotNull();
        }

        @Order(2)
        @ParameterizedTest
        @MethodSource("getArgumentsCreateUser_fail_insufficient_params")
        @DisplayName("인자 불충분하여 실패한다.")
        void createUser_fail_insufficient_params(String caseMsg, String email, String password, String name) {
            // given
            final String targetUrl = "/users/new";
            final CreateUserRequest createUserRequest = new CreateUserRequest();
            createUserRequest.setEmail(email);
            createUserRequest.setPassword(password);
            createUserRequest.setName(name);

            // when
            ExtractableResponse<Response> response = RestAssured
                    /* given 절은 어떤 http request 를 보내줄지 지정 */
                    .given().log().all()
                    .body(createUserRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    /* when 절은 이제 어떠한 URI API 호출할 것인지 지정 */
                    .when()
                    .post(targetUrl)
                    /* then 절은 결과를 리턴 */
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.body().jsonPath().getString("message")).isNotNull();
        }

        static Stream<Arguments> getArgumentsCreateUser_fail_insufficient_params() {
            final User sampleUser = UserTest.getSampleUser(null);
            return getParamStreamArguments(true,
                    sampleUser.getEmail().toString(),
                    sampleUser.getPassword().toString(),
                    sampleUser.getName()
            );
        }

    }

    @Nested
    @Order(2)
    @DisplayName("사용자 로그인 테스트")
    class LoginUserTest {

        @BeforeAll
        void setUpBeforeAll () {
            System.out.println("test");
        }

        @Test
        @Order(1)
        @DisplayName("로그인 성공")
        void loginUser_success() {
            // given
            final String targetUrl = "/users/login";
            User sampleUser = UserTest.getSampleUser(null);
            final String loginEmail = sampleUser.getEmail().toString();
            final String loginPassword = sampleUser.getPassword().toString();

            final LoginUserRequest loginUserRequest = LoginUserRequest.builder()
                    .email(loginEmail)
                    .password(loginPassword)
                    .build();

            // when
            ExtractableResponse<Response> response = RestAssured
                    .given().log().all()
                    .body(loginUserRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .post(targetUrl)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.body().jsonPath().getString("accessToken")).isNotBlank();
            assertThat(response.body().jsonPath().getString("expiredAt")).isNotBlank();
        }

        @Test
        @Order(2)
        @DisplayName("로그인 실패 - 아이디 비밀번호 맞지 않는 경우")
        void loginUser_fail() {
            // given
            final String targetUrl = "/users/login";
            final String loginEmail = "wrong@gmail.com";
            final String loginPassword = "wrong!@#$";

            final LoginUserRequest loginUserRequest = LoginUserRequest.builder()
                    .email(loginEmail)
                    .password(loginPassword)
                    .build();

            // when
            ExtractableResponse<Response> response = RestAssured
                    .given().log().all()
                    .body(loginUserRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .post(targetUrl)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

    }

}
