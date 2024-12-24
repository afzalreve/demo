package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.service.OrderService;
import org.javers.core.Javers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private Javers javers;

    @PostMapping
    public void createOrder(@RequestBody OrderDTO order) {
        orderService.createOrder(order);
    }

    @PostMapping("/update")
    public void updateOrder(@RequestBody OrderDTO order) {
        orderService.updateOrder(order);
        javers.commit("author", order);
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @Autowired
    private DataSource dataSource;

    @GetMapping("/testConnection")
    public String testConnection() {
        try (Connection connection = dataSource.getConnection()) {
            return "Connection successful!";
        } catch (SQLException e) {
            return "Connection failed: " + e.getMessage();
        }
    }
}
