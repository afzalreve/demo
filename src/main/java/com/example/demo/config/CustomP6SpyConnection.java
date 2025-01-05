//package com.example.demo.config;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//class CustomP6SpyConnection extends com.p6spy.engine.spy.P6SpyConnection {
//
//    public CustomP6SpyConnection(Connection connection) {
//        super(connection);
//    }
//
//    @Override
//    public Statement createStatement() throws SQLException {
//        // Custom logic before statement execution
//        System.out.println("Creating a statement. Custom logic before execution.");
//        return new CustomP6SpyStatement(super.createStatement()); // Wrap the statement
//    }
//
//    @Override
//    public PreparedStatement prepareStatement(String sql) throws SQLException {
//        // Custom logic before SQL execution
//        System.out.println("Preparing statement: " + sql);
//        return new CustomP6SpyPreparedStatement(super.prepareStatement(sql)); // Wrap the prepared statement
//    }
//}
