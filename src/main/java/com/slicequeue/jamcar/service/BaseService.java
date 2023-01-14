package com.slicequeue.jamcar.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseService {

    protected void logi(String message) {
        log.info("{} > {}", getServiceName(), message);
    }

    protected void loge(String message) {
        log.error("{} > {}", getServiceName(), message);
    }

    protected abstract String getServiceName();
}
