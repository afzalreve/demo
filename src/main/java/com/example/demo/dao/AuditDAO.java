package com.example.demo.dao;

import com.example.demo.entity.Audit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Repository
public class AuditDAO {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String jdbcUsername;

    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    public void logAudit(Audit audit) {
        String sql = "INSERT INTO audit_logs (action, table_name, record_id, timestamp, user_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, audit.getAction());
            preparedStatement.setString(2, audit.getTableName());
            preparedStatement.setLong(3, audit.getRecordId());
//            preparedStatement.setObject(4, audit.getTimestamp());
            preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(audit.getTimestamp()));
            preparedStatement.setLong(5, audit.getUserId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error logging audit: " + e.getMessage(), e);
        }
    }
}
