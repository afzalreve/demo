package com.example.demo.dao;

import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.Audit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;

@Repository
public class OrderDAO {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String jdbcUsername;

    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    private final AuditDAO auditDAO;

    public OrderDAO(AuditDAO auditDAO) {
        this.auditDAO = auditDAO;
    }

    public void saveOrder(OrderDTO order) {
        String sql = "INSERT INTO orders (user_id, product_id, quantity) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setLong(2, order.getProductId());
            preparedStatement.setInt(3, order.getQuantity());
            int rowsAffected = preparedStatement.executeUpdate();

            // Get the generated order ID
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long orderId = generatedKeys.getLong(1);

                // Log the audit
                Audit audit = new Audit();
                audit.setAction("INSERT");
                audit.setTableName("orders");
                audit.setRecordId(orderId);
                audit.setTimestamp(LocalDateTime.now());
                audit.setUserId(order.getUserId());
                auditDAO.logAudit(audit);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error saving order: " + e.getMessage(), e);
        }
    }

    public void deleteOrder(Long orderId) {
        String sql = "DELETE FROM orders WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, orderId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Log the audit
                Audit audit = new Audit();
                audit.setAction("DELETE");
                audit.setTableName("orders");
                audit.setRecordId(orderId);
                audit.setTimestamp(LocalDateTime.now());
                auditDAO.logAudit(audit);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting order: " + e.getMessage(), e);
        }
    }

    public void updateOrder(OrderDTO order) {
        String sql = "UPDATE orders SET user_id = ?, product_id = ?, quantity = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setLong(2, order.getProductId());
            preparedStatement.setInt(3, order.getQuantity());
            preparedStatement.setLong(4, order.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                // Log the audit
                Audit audit = new Audit();
                audit.setAction("UPDATE");
                audit.setTableName("orders");
                audit.setRecordId(order.getId());
                audit.setTimestamp(LocalDateTime.now());
                audit.setUserId(order.getUserId());
                audit.setUserId(order.getUserId());
                auditDAO.logAudit(audit);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating order: " + e.getMessage(), e);
        }
    }

    public OrderDTO getOrder(Long id) {
        String sql = "SELECT id, user_id, product_id, quantity, order_date FROM orders WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                OrderDTO order = new OrderDTO();
                order.setId(resultSet.getLong("id"));
                order.setUserId(resultSet.getLong("user_id"));
                order.setProductId(resultSet.getLong("product_id"));
                order.setQuantity(resultSet.getInt("quantity"));
                order.setOrderDate(resultSet.getTimestamp("order_date").toLocalDateTime());
                return order;
            } else {
                throw new RuntimeException("Order not found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching order: " + e.getMessage(), e);
        }
    }
}
