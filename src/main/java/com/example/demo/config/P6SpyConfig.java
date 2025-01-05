//package com.example.demo.config;
//
//import com.example.demo.logging.CustomJdbcEventListener;
//import com.p6spy.engine.spy.P6SpyOptions;
//import jakarta.annotation.PostConstruct;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class P6SpyConfig {
//
//    @PostConstruct
//    public void setCustomJdbcEventListener() {
//        P6SpyOptions.getActiveInstance().setJdbcEventListener(new CustomJdbcEventListener());
//        System.out.println("CustomJdbcEventListener registered programmatically");
//    }
//}
