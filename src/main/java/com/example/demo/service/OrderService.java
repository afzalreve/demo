package com.example.demo.service;

import com.example.demo.dao.OrderDAO;
import com.example.demo.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void createOrder(OrderDTO order) {
        orderDAO.saveOrder(order);
    }


    public OrderDTO getOrderById(Long id) {
        return orderDAO.getOrder(id);
    }

    public void deleteOrder(Long id) {
        orderDAO.deleteOrder(id);
    }
}
