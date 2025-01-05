//package com.example.demo.config;
//
//import com.p6spy.engine.spy.P6DataSource;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class CustomP6DataSource extends P6DataSource {
//
//    public CustomP6DataSource(javax.sql.DataSource dataSource) {
//        super(dataSource); // Wrap the original datasource
//    }
//
//    @Override
//    public Connection getConnection() throws SQLException {
//        Connection connection = super.getConnection();
//        return new CustomP6SpyConnection(connection); // Wrap the connection
//    }
//}
