package com.slicequeue.jamcar.service.crawling.rest_template;

import com.slicequeue.jamcar.common.constants.TargetUrl;
import com.slicequeue.jamcar.dto.CrawlingData;
import com.slicequeue.jamcar.service.crawling.Crawling;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * RestTemplateCrawling RestTemplate 을 사용한 API 호출용 크롤링 클래스
 *  * - Crawling 인터페이스의 ConcreteStrategy 클래스
 * - FIXME 현재 작업 중
 */
public class RestTemplateCrawling implements Crawling {

    private static final RestTemplate restTemplate;

    static {
        // Response 한글 깨짐 방지
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        // 공통 header 설정
//        headers = new HttpHeaders();
    }

    public <T> T call(
            HttpMethod method, String url,
            HttpHeaders headers, Object body,
            Class<T> responseClass
    ) {
        HttpEntity<Object> request = new HttpEntity<>(body, headers);
        ResponseEntity<T> response;

        try {

            response = restTemplate.exchange(url, method, request, responseClass);
            if (!response.getStatusCode().is2xxSuccessful()) {
                printErrorResponse(response);
                throw new IllegalStateException("잘못된 응답.");
            }
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

//    public Map call(
//            HttpMethod method, String url,
//            HttpHeaders headers, Object body
//    ) {
//        HttpEntity<Object> request = new HttpEntity<>(body, headers);
//        ResponseEntity<Map> response;
//
//        try {
//
//            response = restTemplate.exchange(
//                    url,
//                    method,
//                    request,
//                    Map.class
//            );
//            if (!response.getStatusCode().is2xxSuccessful()) {
//                printErrorResponse(response);
//                throw new IllegalStateException("잘못된 응답.");
//            }
//            return response.getBody();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }


    private <T> void printErrorResponse(ResponseEntity<T> response) {
        System.out.println("----- resttemplate error response -----");
        System.out.printf("status_code: %s%n", response.getStatusCode());
        System.out.printf("response: %s%n", response.getBody());
        System.out.println("---------------------------------------");
    }

    @Override
    public List<CrawlingData> doCrawling(TargetUrl targetUrl) {
//        call(HttpMethod.GET, targetUrl.url, null, null, (Map<String, Object>));
        // TODO 기능 구현 - 일반 API 호출의 경우 수집이 용이
        return null;
    }
}
