package com.example.demo.dao;

import com.example.demo.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserDAO {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String jdbcUsername;

    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    private final AuditDAO auditDAO;

    public UserDAO( AuditDAO auditDAO) {
        this.auditDAO = auditDAO;
    }

    public UserDTO saveUser(UserDTO user) {
        String sql = "INSERT INTO users (name) VALUES (?)";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getName());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Failed to insert user, no rows affected.");
            }

            // Get the generated user ID
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1)); // Set the generated ID in the user object
            } else {
                throw new RuntimeException("Failed to retrieve the generated user ID.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error saving user: " + e.getMessage(), e);
        }
        return user;
    }

    // Method to update user
    public UserDTO updateUser(UserDTO user) {
        String sql = "UPDATE users SET name = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setLong(2, user.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("User with ID " + user.getId() + " not found.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
        return user;
    }

}
