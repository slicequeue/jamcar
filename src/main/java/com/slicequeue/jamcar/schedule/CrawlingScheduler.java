package com.slicequeue.jamcar.schedule;

import com.slicequeue.jamcar.common.constants.TargetUrl;
import com.slicequeue.jamcar.dto.CrawlingData;
import com.slicequeue.jamcar.service.BaseService;
import com.slicequeue.jamcar.service.JamcarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


@Log4j2
@Service
@RequiredArgsConstructor
public class CrawlingScheduler extends BaseService {

    private final TargetUrl target = TargetUrl.NAVERMAP_HOME_TO_PANGYO;
    private final JamcarService service;

    /**
     * 매 5분 마다 Jamcar 크롤링 진행
     */
    @Scheduled(cron = "0 */5 * * * *")
    public void crawlingJamcar() {
        long time = System.currentTimeMillis();
        logi("crawlingJamcar method > start: thread [{}]", Thread.currentThread().getName());
        List<CrawlingData> crawlingData = service.getCrawlingData(target);
        logi("crawlingJamcar method > crawling data: {}", crawlingData);
        logi("crawlingJamcar method > end: thread [{}] / time {} ms",
                Thread.currentThread().getName(), System.currentTimeMillis() - time);
    }

    @Override
    protected String getServiceName() {
        return CrawlingScheduler.class.getSimpleName();
    }
}
