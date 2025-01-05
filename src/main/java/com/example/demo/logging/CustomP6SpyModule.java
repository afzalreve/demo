//package com.example.demo.logging;
//
//import com.p6spy.engine.event.JdbcEventListener;
//import com.p6spy.engine.spy.option.P6OptionsRepository;
//
//public class CustomP6SpyModule implements P6Module {
//    public CustomP6SpyModule( P6OptionsRepository optionsRepository) {
//        System.out.println("CustomP6SpyModule initialized");
//    }
//
//    @Override
//    public JdbcEventListener getJdbcEventListener() {
//        return new CustomJdbcEventListener();
//    }
//}
