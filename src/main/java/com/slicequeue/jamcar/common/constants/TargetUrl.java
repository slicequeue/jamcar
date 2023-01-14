package com.slicequeue.jamcar.common.constants;

import com.slicequeue.jamcar.service.crawling.selenium.extract.Extract;
import com.slicequeue.jamcar.service.crawling.selenium.extract.NaverMapExtract;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TargetUrl {

    NAVERMAP_HOME_TO_PANGYO("https://map.naver.com/v5/directions/" +
            "14154447.692006778,4515760.99243252,%EB%AA%85%EC%9D%BC%EC%A3%BC%EA%B3%B59%EB%8B%A8%EC%A7%80%EC%95%84%ED%8C%8C%ED%8A%B8,18892386,PLACE_POI/" +
            "14149864.223292854,4494373.056117786,%EC%B9%B4%EC%B9%B4%EC%98%A4%20%ED%8C%90%EA%B5%90%EC%95%84%EC%A7%80%ED%8A%B8,35701040,PLACE_POI/" +
            "-/car?c=14143974.9004749,4504789.6936506,11,0,0,0,dh"),
    NAVERMAP_HOME_TO_BUSAN("https://map.naver.com/v5/directions/" +
            "14154447.692006778,4515760.99243252,%EB%AA%85%EC%9D%BC%EC%A3%BC%EA%B3%B59%EB%8B%A8%EC%A7%80%EC%95%84%ED%8C%8C%ED%8A%B8,18892386,PLACE_POI/" +
            "14364913.508788805,4179550.113085119,%EB%B6%80%EC%82%B0%EC%97%AD%20%EA%B2%BD%EB%B6%80%EC%84%A0(%EA%B3%A0%EC%86%8D%EC%B2%A0%EB%8F%84),13479631,PLACE_POI/" +
            "-/car?c=14146391.2675571,4347634.8680213,7,0,0,0,dh")
    ;

    public String url;
}
