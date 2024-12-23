package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public void createOrder(@RequestBody OrderDTO order) {
        orderService.createOrder(order);
    }

    @PostMapping("/update")
    public void updateOrder(@RequestBody OrderDTO order) {
        orderService.updateOrder(order);
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
