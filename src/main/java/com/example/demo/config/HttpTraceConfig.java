package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;

@Configuration
public class HttpTraceConfig {

    private static final Logger logger = LoggerFactory.getLogger(HttpTraceConfig.class);

    @Bean
    public HttpTraceRepository httpTraceRepository() {
        logger.info("HttpTraceRepository Bean Initialized");
        return new InMemoryHttpTraceRepository();
    }
}
