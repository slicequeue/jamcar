package com.slicequeue.jamcar.service.crawling.selenium;

import com.slicequeue.jamcar.service.crawling.selenium.driver.FirefoxWebDriverLoader;
import com.slicequeue.jamcar.service.crawling.selenium.driver.WebDriverLoader;
import com.slicequeue.jamcar.service.crawling.selenium.extract.Extract;
import com.slicequeue.jamcar.service.crawling.selenium.extract.NaverMapExtract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumCrawlingConfigure {

    @Bean
    WebDriverLoader driverLoader() {
        return new FirefoxWebDriverLoader();
    }

    @Bean
    Extract extract() {
        return new NaverMapExtract();
    }

}
