package com.slicequeue.jamcar.service.crawling;

import com.slicequeue.jamcar.common.constants.TargetUrl;
import com.slicequeue.jamcar.dto.CrawlingData;

import java.util.List;

/**
 * Crawling 인터페이스
 * - Crawling 관련 전략 패턴 Strategy 인터페이스
 *   - Context 대상 JamcarService
 */
public interface Crawling {

    List<CrawlingData> doCrawling(TargetUrl targetUrl);
}
