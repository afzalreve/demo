package com.example.demo.dto;

import lombok.Data;
import org.javers.core.metamodel.annotation.Id;
import org.javers.core.metamodel.annotation.TypeName;

import java.time.LocalDateTime;

@Data
@TypeName("OrderDTO")
public class OrderDTO {
    @Id
    private Long id;
    private Long userId;
    private Long productId;
    private int quantity;
    private LocalDateTime orderDate;

}
