package com.slicequeue.jamcar.controller;

import com.slicequeue.jamcar.common.constants.TargetUrl;
import com.slicequeue.jamcar.dto.CrawlingData;
import com.slicequeue.jamcar.service.JamcarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/jamcar")
public class JamcarController {

    private final JamcarService service;

    @GetMapping
    public String getHello() {
        return "hello";
    }

    @GetMapping("/call")
    public List<CrawlingData> getCall(@RequestParam(value = "target-url") TargetUrl targetUrl) {
        return service.getCrawlingData(targetUrl);
    }

}
