package com.slicequeue.jamcar.common;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 인수 테스트 공통 적용 어노테이션
 * <pre>
 * - @SpringBootTest webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT 지정
 * - @TestInstance TestInstance.Lifecycle.PER_CLASS 인스턴스 하나로 적용!
 *   - @Nested 활용할 경우 트렌젝션 롤백하지 않도록 유지함! 이점 유의할 것
 * - @TestClassOrder(ClassOrderer.OrderAnnotation.class) Order 어노테이션 기반으로 적용
 *   - 테스트에서 순서 지정은 @Order 활용하여 처리하도록 할 것
 * - @TestExecutionListeners 테스트 실행 리스터 등록
 *   - AcceptanceTestExecutionListener.class 지정하여 인수테스트시 트렌젝션 롤벡 컨트롤하는 커스텀 리스너 지정
 *   - 테스트하는 데이터베이스 종류에 따라 외래키 제약조건 해제 후 모든 테이블 데이터 TRUNCATE 처리
 * </pre>
 * @author slicequeue
 */
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class},
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
public @interface AcceptanceTest {
}
