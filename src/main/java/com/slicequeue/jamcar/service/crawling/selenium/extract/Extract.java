package com.slicequeue.jamcar.service.crawling.selenium.extract;

import com.slicequeue.jamcar.dto.CrawlingData;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * Extract 셀레니움에서 해당 주소로 응답에 대하여 추출에 대한 인터페이스
 * - 템플릿 콜백 메소드 패턴 중 Callback 인터페이스
 *   - AbstractClass 클래스 대상 SeleniumFirefoxCrawling, 해당 클래스 내 doCrawling 부분
 */
public interface Extract {

    List<CrawlingData> doExtract(WebDriver driver);

}
