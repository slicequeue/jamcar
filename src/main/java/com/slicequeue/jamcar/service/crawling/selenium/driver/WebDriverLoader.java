package com.slicequeue.jamcar.service.crawling.selenium.driver;

import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * WebDriverLoader 인터페이스
 * - Selenium 에 사용할 Web Browser 용 Driver 로딩 처리
 *   - Context 대상 JamcarService
 */
public interface WebDriverLoader {

    RemoteWebDriver load();
}
