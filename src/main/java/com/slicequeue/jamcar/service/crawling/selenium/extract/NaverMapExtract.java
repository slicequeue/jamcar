package com.slicequeue.jamcar.service.crawling.selenium.extract;

import com.slicequeue.jamcar.dto.CrawlingData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * NaverMapExtract 네이버 지도에서 검색한 결과를 해석하하여 CrawlingData 객체 생성
 * - 템플릿 콜백 메소드 패턴 중 ConcreteCallback 클래스
 *   - Callback 인터페이스 대상 Extract
 */
public class NaverMapExtract implements Extract {


    @Override
    public List<CrawlingData> doExtract(WebDriver driver) {
        List<CrawlingData> result = new ArrayList<>();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement parent = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ul.search_result_list")));
        List<WebElement> elementList = parent.findElements(By.cssSelector("ul.search_result_list directions-summary-item-car"));
        LocalDateTime now = LocalDateTime.now();
        for (WebElement element : elementList) {
            String duration = element.findElement(By.cssSelector("strong.duration_time readable-duration")).getText();
            String distance = element.findElement(By.cssSelector("span.summary_info readable-distance")).getText();
            String etcFee = element.findElement(By.cssSelector("div.route_summary_info_area ul")).getText();
            String route = element.findElement(By.cssSelector("directions-summary-item-car-route-list")).getText();
            result.add(CrawlingData.from(duration, distance, etcFee, route, now));
        }
        return result;
    }
}
