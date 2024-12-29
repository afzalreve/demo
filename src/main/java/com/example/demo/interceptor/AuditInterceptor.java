package com.example.demo.interceptor;

import com.example.demo.dao.AuditDAO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.AuditDTO;
import com.example.demo.util.CachedBodyHttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;


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

            System.out.println("HTTP Method: " + request.getMethod());
            System.out.println("Request URI: " + request.getRequestURI());

            System.out.println("Headers:");
            request.getHeaderNames().asIterator().forEachRemaining(headerName ->
                    System.out.println(headerName + ": " + request.getHeader(headerName))
            );

            System.out.println("Query Parameters:");
            request.getParameterMap().forEach((key, value) ->
                    System.out.println(key + ": " + String.join(", ", value))
            );

            if (!action.equals("UNKNOWN") && request.getMethod().equalsIgnoreCase("POST")) {
                CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request);

                String requestBody = new String(cachedRequest.getCachedBody(), StandardCharsets.UTF_8);
                System.out.println("Request Body: " + requestBody);

                UserDTO userDTO = objectMapper.readValue(requestBody, UserDTO.class);
                System.out.println("Deserialized UserDTO: " + userDTO);

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
