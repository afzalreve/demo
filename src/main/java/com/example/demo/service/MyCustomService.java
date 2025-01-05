package com.example.demo.service;

public class MyCustomService {
    public static void logDatabaseUpdate(String sql) {
        // Implement your custom logic here
        System.out.println("Logging Update: " + sql);
        // Perform any additional processing, such as auditing or triggering other operations
    }
}
