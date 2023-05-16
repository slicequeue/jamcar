package com.slicequeue.jamcar.user.presentation;

import com.slicequeue.jamcar.user.command.application.CreateUserRequest;
import com.slicequeue.jamcar.user.command.application.LoginUserRequest;
import com.slicequeue.jamcar.user.command.domain.User;
import com.slicequeue.jamcar.user.command.domain.UserRepository;
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
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Stream;

import static com.slicequeue.jamcar.util.ParameterizedTestUtil.getParamStreamArguments;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class UsersControllerE2ENestedTest {

    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        System.out.println("setUp1");
        RestAssured.port = port;
        System.out.println(RestAssured.port);
    }

    @AfterAll
    public void afterAll() {
        final List<String> truncateQueries = getTruncateQueries(jdbcTemplate);
        truncateTables(jdbcTemplate, truncateQueries);
    }

    private List<String> getTruncateQueries(final JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForList("SELECT Concat('TRUNCATE TABLE ', TABLE_NAME, ';') AS q FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'", String.class);
    }

    private void truncateTables(final JdbcTemplate jdbcTemplate, final List<String> truncateQueries) {
        execute(jdbcTemplate, "SET REFERENTIAL_INTEGRITY FALSE");
        truncateQueries.forEach(v -> execute(jdbcTemplate, v));
        execute(jdbcTemplate, "SET REFERENTIAL_INTEGRITY TRUE");
    }

    private void execute(final JdbcTemplate jdbcTemplate, final String query) {
        jdbcTemplate.execute(query);
    }

    @Nested
    @Order(1)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("사용사 생성 테스트")
    class CreateUserTest {

        final String targetUrl = "/users/new";
        final User sampleUser = UserTest.getSampleUser(new UserUid());

        @Order(1)
        @ParameterizedTest
        @MethodSource("getArgumentsCreateUser_fail_case1_insufficient_params")
        @DisplayName("인자 불충분하여 실패한다.")
        void createUser_fail_insufficient_params(String caseMsg, String email, String password, String name) {
            // given
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

        static Stream<Arguments> getArgumentsCreateUser_fail_case1_insufficient_params() {
            final User sampleUser = UserTest.getSampleUser(null);
            return getParamStreamArguments(true,
                    sampleUser.getEmail().toString(),
                    sampleUser.getPassword(),
                    sampleUser.getName()
            );
        }

        @Test
        @Order(2)
        @DisplayName("사용자 계정 생성 성공한다.")
        void createUser_success() {
            // given
            final CreateUserRequest createUserRequest = new CreateUserRequest();
            createUserRequest.setEmail(sampleUser.getEmail().toString());
            createUserRequest.setPassword(sampleUser.getPassword());
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

        @Order(3)
        @Test
        @DisplayName("사용자 계정 중복으로 실패한다.")
        void createUser_fail_duplicated() {
            // given
            final CreateUserRequest createUserRequest = new CreateUserRequest();
            createUserRequest.setEmail(sampleUser.getEmail().toString());
            createUserRequest.setPassword(sampleUser.getPassword());
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
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CONFLICT.value());
            assertThat(response.body().jsonPath().getString("message")).isNotNull();

        }

    }

    @Nested
    @Order(2)
    @TestInstance(Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("사용자 로그인 테스트")
    class LoginUserTest {

        final String targetUrl = "/users/login";
        final User sampleUser = UserTest.getSampleUser(null);

        @Autowired
        UserRepository userRepository;

        @BeforeAll
        void init() {
            // 위에 CreateUserTest 의해 해당 생성한 샘플 아이디가 존재함
            // @TestInstance(Lifecycle.PER_CLASS) 에 의해 트렌젝션 유지~
//            User sampleUser = UserTest.getSampleUser(new UserUid());
//            userRepository.save(sampleUser);
        }

        @Order(1)
        @ParameterizedTest
        @MethodSource("getArguments_loginUser_fail_id_password_invalid")
        @DisplayName("아이디 비밀번호 형식이 맞지 않아 실패한다.")
        void loginUser_fail_id_password_invalid(String loginEmail, String loginPassword) {
            // given
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

        private static Stream<Arguments> getArguments_loginUser_fail_id_password_invalid() {
            final String wrongEmail = "wrong.com";
            final String wrongPassword = "wrong";
            return Stream.of(
                    Arguments.of(null, null),
                    Arguments.of(wrongEmail, null),
                    Arguments.of(null, wrongPassword),
                    Arguments.of(wrongEmail, wrongPassword)
            );
        }

        @Test
        @Order(2)
        @DisplayName("계정이 존재하지 않아 실패한다.")
        void loginUser_fail_user_not_exist() {
            // given
            final String loginEmail = "wrong@fake.com";
            final String loginPassword = "wrong!@#123";

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

        @Test
        @Order(3)
        @DisplayName("비밀번호가 맞지 않아 실패한다.")
        void loginUser_fail_not_matched_password() {
            // given
            final String loginEmail = sampleUser.getEmail().toString();
            final String loginPassword = "wrong!@#123";

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

        @Test
        @Order(4)
        @DisplayName("로그인에 성공한다.")
        void loginUser_success() {
            // given
            final String loginEmail = sampleUser.getEmail().toString();
            final String loginPassword = sampleUser.getPassword();

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

    }

}
