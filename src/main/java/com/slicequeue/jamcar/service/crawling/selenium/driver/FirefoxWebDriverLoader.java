package com.slicequeue.jamcar.service.crawling.selenium.driver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * FirefoxWebDriverLoader 파이어폭스 웹 드라이버 로드 수행
 * - 전략 패턴 중 ConcreteStrategy 클래스
 *   - Strategy 인터페이스 대상 WebDriverLoader
 */
public class FirefoxWebDriverLoader implements WebDriverLoader {
    public static final String DRIVER_PROPERTY =
            "webdriver.gecko.driver";

    public static final String GECKO_DRIVER_PATH =
            "src/main/resources/driver/geckodriver.exe";

    @Override
    public RemoteWebDriver load() {
        Path path = FileSystems.getDefault().getPath(GECKO_DRIVER_PATH);
        System.setProperty(DRIVER_PROPERTY, path.toString());
        return new FirefoxDriver();
    }
}
