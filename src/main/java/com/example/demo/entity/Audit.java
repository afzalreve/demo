package com.example.demo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Audit {
    private Long id;
    private String action; // INSERT, UPDATE, DELETE
    private String tableName;
    private Long recordId;
    private LocalDateTime timestamp;
    private Long userId;
}
