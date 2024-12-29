package com.example.demo.interceptor;

import com.example.demo.dao.AuditDAO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.AuditDTO;
import com.example.demo.util.CachedBodyHttpServletRequest;
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
        if (handler instanceof HandlerMethod handlerMethod) {
            String methodName = handlerMethod.getMethod().getName();
            String action = determineAction(methodName);

            if (!action.equals("UNKNOWN") && request.getMethod().equals("POST")) {
                CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request);
                UserDTO userDTO = objectMapper.readValue(cachedRequest.getInputStream(), UserDTO.class);
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

    private void logAudit(UserDTO userDTO, String action) {
        AuditDTO auditDTO = new AuditDTO();
        auditDTO.setAction(action);
        auditDTO.setTableName("users");
        auditDTO.setRecordId(userDTO.getId());
        auditDAO.logAudit(auditDTO);
    }
}
