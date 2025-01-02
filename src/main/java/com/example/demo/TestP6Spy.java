package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestP6Spy {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:p6spy:mysql://localhost:3306/loggerdemo";
        String jdbcUsername = "root";
        String jdbcPassword = "@3unfiled";

        String sql = "SELECT 1";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
