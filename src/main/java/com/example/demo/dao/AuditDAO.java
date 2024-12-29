package com.example.demo.dao;

import com.example.demo.dto.AuditDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class AuditDAO {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String jdbcUsername;

    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    public void logAudit( AuditDTO auditDTO ) {
        System.out.println("inside auditDAO.logAudit");
        String sql = "INSERT INTO audit (action, table_name, record_id) VALUES (?, ?, ?)";
        System.out.println("sql: "+sql);
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, auditDTO.getAction());
            preparedStatement.setString(2, auditDTO.getTableName());
            preparedStatement.setLong(3, auditDTO.getRecordId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error logging audit: " + e.getMessage(), e);
        }
    }
}
