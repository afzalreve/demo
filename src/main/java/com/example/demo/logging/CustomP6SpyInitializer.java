//package com.example.demo.logging;
//
//import com.p6spy.engine.spy.P6SpyOptions;
//import com.p6spy.engine.event.JdbcEventListenerFactory;
//import com.p6spy.engine.event.JdbcEventListener;
//
//public class CustomP6SpyInitializer {
//
//    public static void initialize() {
//        P6SpyOptions.getActiveInstance()
//                .loadFactory("jdbc.event.listener", CustomJdbcEventListenerFactory.class.getName());
//    }
//
//    public static class CustomJdbcEventListenerFactory implements JdbcEventListenerFactory {
//        @Override
//        public JdbcEventListener createJdbcEventListener() {
//            return new CustomJdbcEventListener();
//        }
//    }
//}
