package com.slicequeue.jamcar.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseService {

    String baseFormat = "{} > ";

    protected void logi(String message) {
        log.info("{} > {}", getServiceName(), message);
    }

    protected  void logi(String logFormat, Object... args) {
        String format = baseFormat.concat(logFormat);
        log.info(format, getServiceName(), args);
    }

    protected void loge(String message) {
        log.error("{} > {}", getServiceName(), message);
    }

    protected  void loge(String logFormat, Object [] ...args) {
        String format = baseFormat.concat(logFormat);
        log.error(format, getServiceName(), args);
    }

    protected abstract String getServiceName();
}
