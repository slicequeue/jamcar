package com.slicequeue.jamcar.user.presentation;

import com.slicequeue.jamcar.user.command.application.CreateUserRequest;
import com.slicequeue.jamcar.user.command.domain.User;
import com.slicequeue.jamcar.user.command.domain.UserTest;
import com.slicequeue.jamcar.user.command.domain.UserUid;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * @DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)는 JUnit5 프레임워크에서 제공하는 애너테이션 중 하나입니다.
 * 이 애너테이션은 각각의 테스트 메소드 실행 전에 스프링 애플리케이션 컨텍스트를 다시 로드하도록 지시합니다.
 * 스프링 애플리케이션 컨텍스트는 스프링 애플리케이션에서 사용하는 빈, 데이터 소스, 트랜잭션 매니저 및 다른 스프링 관리 객체를 포함하는 중요한 컴포넌트입니다.
 * 일반적으로 스프링 애플리케이션 컨텍스트는 테스트 클래스 수준에서 만들어지며, 각각의 테스트 메소드에서 재사용됩니다.
 * 그러나 @DirtiesContext 애너테이션을 사용하면 각 테스트 메소드에서 컨텍스트를 다시 로드할 수 있으므로
 * 특정 테스트가 이전 테스트의 결과에 영향을 받지 않고 독립적으로 실행될 수 있습니다.
 * 이는 일부 통합 테스트에서 유용하며, 특정 테스트 간의 의존성을 줄이고 테스트의 격리성을 보장합니다.
 * 다만, @DirtiesContext 애너테이션은 애플리케이션 컨텍스트를 다시 로드하기 때문에 성능에 영향을 미칠 수 있으므로,
 * 가능한 한 사용을 자제해야 합니다.
 */
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsersControllerE2ETest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    @DisplayName("사용자 등록 성공 - case1 정상 입력")
    void createUser_success_case1() {
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

}
