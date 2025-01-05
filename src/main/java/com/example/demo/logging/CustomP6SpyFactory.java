//package com.example.demo.logging;
//
//import com.p6spy.engine.spy.P6Factory;
//import com.p6spy.engine.event.JdbcEventListener;
//import com.p6spy.engine.spy.P6LoadableOptions;
//import com.p6spy.engine.spy.option.P6OptionsRepository;
//
//public class CustomP6SpyFactory implements P6Factory {
//    @Override
//    public P6LoadableOptions getOptions( P6OptionsRepository p6OptionsRepository )
//    {
//        return null;
//    }
//
//    @Override
//    public JdbcEventListener getJdbcEventListener()
//    {
//        return new CustomJdbcEventListener();
//    }
//}
