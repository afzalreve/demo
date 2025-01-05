//package com.example.demo.config;
//
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//class CustomP6SpyStatement extends P6SpyStatement {
//
//    public CustomP6SpyStatement( Statement statement) {
//        super(statement);
//    }
//
//    @Override
//    public boolean execute() throws SQLException
//    {
//        // Custom logic before executing the statement
//        System.out.println("Executing statement: " + getSql());
//        return super.execute();
//    }
//
//    @Override
//    public PreparedStatement prepareStatement( String sql) throws SQLException {
//        // Custom logic for preparing statements
//        System.out.println("Preparing SQL in custom statement: " + sql);
//        return super.prepareStatement(sql);
//    }
//}
