package com.example.demo.interceptor;

import com.example.demo.dao.AuditDAO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.Audit;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;


@Component
public class AuditInterceptor implements HandlerInterceptor {

    private final AuditDAO auditDAO;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuditInterceptor(AuditDAO auditDAO, ObjectMapper objectMapper) {
        this.auditDAO = auditDAO;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("inside handler");
        if (handler instanceof HandlerMethod handlerMethod) {
            String methodName = handlerMethod.getMethod().getName();
            String action = determineAction(methodName);

            if (!action.equals("UNKNOWN") && request.getMethod().equals("POST")) {
                ServletInputStream inputStream = request.getInputStream();
                OrderDTO order = objectMapper.readValue(inputStream, OrderDTO.class);

                logAudit(order, action);
            }
        }
        return true;
    }

    private String determineAction(String methodName) {
        return switch (methodName) {
            case "createOrder" -> "INSERT";
            case "updateOrder" -> "UPDATE";
            default -> "UNKNOWN";
        };
    }

    private void logAudit(OrderDTO order, String action) {
        Audit audit = new Audit();
        audit.setAction(action);
        audit.setTableName("orders");
        audit.setRecordId(order.getId());
        audit.setTimestamp(LocalDateTime.now());
        audit.setUserId(order.getUserId());
        auditDAO.logAudit(audit);
    }
}
