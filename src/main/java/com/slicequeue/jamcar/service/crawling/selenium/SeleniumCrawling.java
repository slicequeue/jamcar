package com.slicequeue.jamcar.service.crawling.selenium;

import com.slicequeue.jamcar.common.constants.TargetUrl;
import com.slicequeue.jamcar.dto.CrawlingData;
import com.slicequeue.jamcar.service.crawling.Crawling;
import com.slicequeue.jamcar.service.crawling.selenium.driver.WebDriverLoader;
import com.slicequeue.jamcar.service.crawling.selenium.extract.Extract;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * SeleniumCrawling 셀레니움을 웹브라우저 크롤링 클래스
 * - Crawling 인터페이스의 ConcreteStrategy 클래스
 */
@Component
@RequiredArgsConstructor
public class SeleniumCrawling implements Crawling {

    private final WebDriverLoader driverLoader;

    private final Extract extract;

    private WebDriver init() {
        return driverLoader.load();
    }

    @Override
    public List<CrawlingData> doCrawling(TargetUrl targetUrl) {
        WebDriver driver = init();
        driver.get(targetUrl.url);
        List<CrawlingData> result = extract.doExtract(driver);
        driver.quit();
        return result;
    }

}
