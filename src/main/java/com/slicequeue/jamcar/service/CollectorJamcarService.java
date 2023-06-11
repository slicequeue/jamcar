package com.slicequeue.jamcar.service;

import com.slicequeue.jamcar.common.constants.TargetUrl;
import com.slicequeue.jamcar.dto.CrawlingData;
import com.slicequeue.jamcar.service.crawling.Crawling;
import com.slicequeue.jamcar.service.crawling.selenium.SeleniumCrawling;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectorJamcarService {

    private Crawling crawling;

    public CollectorJamcarService(SeleniumCrawling crawling) {
        this.crawling = crawling;
    }

    public List<CrawlingData> getCrawlingData(TargetUrl targetUrl) {
        return crawling.doCrawling(targetUrl);
    }
}
