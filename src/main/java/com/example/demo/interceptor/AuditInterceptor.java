package com.example.demo.interceptor;

import com.example.demo.dao.AuditDAO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.AuditDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;


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
            System.out.println("methodName: "+methodName);
            String action = determineAction(methodName);
            System.out.println("action: " + action);

            if (!action.equals("UNKNOWN") && request.getMethod().equals("POST")) {
                System.out.println("inside not unknown");
                ServletInputStream inputStream = request.getInputStream();
                System.out.println("inputStream: " + inputStream);
                UserDTO userDTO = objectMapper.readValue(inputStream, UserDTO.class);
                System.out.println("userDTO: "+userDTO);
                System.out.println("userDTO id: "+userDTO.getId());

                logAudit(userDTO, action);
            }
        }
        return true;
    }

    private String determineAction(String methodName) {
        return switch (methodName) {
            case "saveUser" -> "INSERT";
            case "updateUser" -> "UPDATE";
            default -> "UNKNOWN";
        };
    }

    private void logAudit( UserDTO order, String action) {
        AuditDTO auditDTO = new AuditDTO();
        auditDTO.setAction(action);
        auditDTO.setTableName("users");
        auditDTO.setRecordId(order.getId());
        auditDAO.logAudit(auditDTO);
    }
}
