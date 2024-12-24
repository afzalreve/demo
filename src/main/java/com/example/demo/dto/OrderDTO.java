package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private int quantity;
    private LocalDateTime orderDate;

}
