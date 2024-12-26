package com.example.demo.dto;

import lombok.Data;

@Data
public class AuditDTO {
    private Long id;
    private String action; // INSERT, UPDATE, DELETE
    private String tableName;
    private Long recordId;
}
