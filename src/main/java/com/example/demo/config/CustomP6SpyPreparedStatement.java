//package com.example.demo.config;
//
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//class CustomP6SpyPreparedStatement extends P6SpyPreparedStatement {
//
//    public CustomP6SpyPreparedStatement(PreparedStatement stmt) {
//        super(stmt);
//    }
//
//    @Override
//    public boolean execute() throws SQLException {
//        // Custom logic before executing the prepared statement
//        System.out.println("Custom logic before executing SQL: " + getSql());
//        return super.execute();
//    }
//}
