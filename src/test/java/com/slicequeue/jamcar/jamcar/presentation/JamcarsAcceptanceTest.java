package com.slicequeue.jamcar.jamcar.presentation;

import com.slicequeue.jamcar.common.AcceptanceTest;
import com.slicequeue.jamcar.jamcar.command.application.CreateJamcarRequest;
import com.slicequeue.jamcar.util.ParameterizedTestUtil;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@AcceptanceTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(JamcarsController.class)
public class JamcarsAcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Nested
    @Order(1)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("잼카 생성 테스트")
    class CreateJamcar {

        final String targetUrl = "/jamcars/new";

        @Order(1)
        @Test
        @DisplayName("테스트 사용자 로그인 액세스토큰 발급")
        void login_test_user() {
            // given


            // when

            // then
        }


        @Order(2)
        @ParameterizedTest
        @MethodSource("getArguments_createJamcar_fail_not_sufficient_param")
        @DisplayName("필수인자 불충분하여 실패한다.")
        void createJamcar_fail_not_sufficient_param(String msgKey, String fromAddress, String toAddress) {
            // given
            final CreateJamcarRequest request = CreateJamcarRequest.builder()
                    .fromAddress(fromAddress)
                    .toAddress(toAddress)
                    .build();

            // when
            ExtractableResponse<Response> response = RestAssured
                    .given().log().all()
                    .body(request)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .post(targetUrl)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.body().jsonPath().getString("message")).isNotNull();
        }

        public static Stream<Arguments> getArguments_createJamcar_fail_not_sufficient_param() {
            final String fromAddress = "출발목적지";
            final String toAddress = "도착목적지";
            return ParameterizedTestUtil.getParamStreamArguments(true, fromAddress, toAddress);
        }

        @Test
        @Order(3)
        @DisplayName("성공한다.")
        void createJamcar_success() {
            // given
            final String fromAddress = "출발지1";
            final String toAddress = "도착지1";
            final CreateJamcarRequest request = CreateJamcarRequest.builder()
                    .fromAddress(fromAddress)
                    .toAddress(toAddress)
                    .build();

            // when
            ExtractableResponse<Response> response = RestAssured
                    .given().log().all()
                    .body(request)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .post(targetUrl)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.body().jsonPath().getString("$.id")).isNotNull();

        }


        @Order(4)
        @Test
        @DisplayName("사용자, 출발지, 도착지 같은 경우 실행상태 완료 아닌 경우  실패한다.")
        void createJamcar_fail_duplicated_business_key() {
            // given
            final String fromAddress = "출발지1";
            final String toAddress = "도착지1";
            final CreateJamcarRequest request = CreateJamcarRequest.builder()
                    .fromAddress(fromAddress)
                    .toAddress(toAddress)
                    .build();

            // when
            ExtractableResponse<Response> response = RestAssured
                    .given().log().all()
                    .body(request)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .post(targetUrl)
                    .then().log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CONFLICT.value());

        }


    }

}
