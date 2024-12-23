package com.example.demo.dao;

import com.example.demo.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDAO {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String jdbcUsername;

    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    public void saveOrder(OrderDTO order) {
        String sql = "INSERT INTO orders (user_id, product_id, quantity) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setLong(2, order.getProductId());
            preparedStatement.setInt(3, order.getQuantity());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving order: " + e.getMessage(), e);
        }
    }

    public List<OrderDTO> getAllOrders() {
        String sql = "SELECT * FROM orders";
        List<OrderDTO> orders = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                orders.add(mapRowToOrderDTO(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all orders: " + e.getMessage(), e);
        }

        return orders;
    }

    public OrderDTO getOrderById(Long id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToOrderDTO(resultSet);
                } else {
                    throw new RuntimeException("Order not found with ID: " + id);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching order by ID: " + e.getMessage(), e);
        }
    }

    public void deleteOrder(Long id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting order: " + e.getMessage(), e);
        }
    }

    private OrderDTO mapRowToOrderDTO(ResultSet resultSet) throws SQLException {
        OrderDTO order = new OrderDTO();
        order.setId(resultSet.getLong("id"));
        order.setUserId(resultSet.getLong("user_id"));
        order.setProductId(resultSet.getLong("product_id"));
        order.setQuantity(resultSet.getInt("quantity"));
        order.setOrderDate(resultSet.getTimestamp("order_date").toLocalDateTime());
        return order;
    }
}
