package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;

@Component
public class HttpTraceLogger {

    private static final Logger logger = LoggerFactory.getLogger(HttpTraceLogger.class);

    @Autowired
    private HttpTraceRepository httpTraceRepository;

    @Scheduled(fixedRate = 4000) // Log every x miliseconds
    public void logHttpTraces() {
        var traces = httpTraceRepository.findAll();
        if (traces.isEmpty()) {
            logger.info("No HTTP Traces Found");
        } else {
            traces.forEach(trace -> {
                logger.info("Request: Method={}, URI={}, Status={}, TimeTaken={}ms",
                        trace.getRequest().getMethod(),
                        trace.getRequest().getUri(),
                        trace.getResponse().getStatus(),
                        trace.getTimeTaken());
            });
        }
    }
}
