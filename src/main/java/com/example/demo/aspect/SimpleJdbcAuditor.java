package com.example.demo.aspect;

import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.Audit;
import com.example.demo.dao.AuditDAO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class SimpleJdbcAuditor {

    @Autowired
    private AuditDAO auditDAO;

    // Intercepts saveOrder and updateOrder methods in OrderDAO
    @Around("execution(* com.example.demo.dao.OrderDAO.*Order(..))")
    public Object auditJdbcOperations(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (args[0] instanceof OrderDTO order) {
            // Determine the method name to decide the action
            String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
            String action = determineAction(methodName);

            // Proceed with the original method
            Object result = joinPoint.proceed();

            // Log the audit
            if (!action.equals("UNKNOWN")){
                logAudit(order, action);
            }

            return result;
        }
        return joinPoint.proceed();
    }

    private String determineAction(String methodName) {
        return switch (methodName) {
            case "saveOrder" -> "INSERT";
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
