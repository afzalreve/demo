package com.example.demo.aspect;


import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.Audit;
import com.example.demo.dao.AuditDAO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class SimpleJdbcAuditor {

    @Autowired
    private AuditDAO auditDAO;  // Assuming you have AuditDAO to log the audit

    // Intercepts methods in DAO layer
    @Around("execution(* com.example.demo.dao.OrderDAO.saveOrder(..))")  // Adjust the method and package as needed
    public Object auditJdbcOperations(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        OrderDTO order = (OrderDTO) args[0];  // Assuming the first argument is OrderDTO

        // Proceed with the original method
        Object result = joinPoint.proceed();

        // Log audit after the operation
        logAudit(order);

        return result;
    }

    private void logAudit(OrderDTO order) {
        Audit audit = new Audit();
        audit.setAction("INSERT");
        audit.setTableName("orders");
        audit.setRecordId(order.getId());  // Assuming 'id' is set after the insert
        audit.setTimestamp(LocalDateTime.now());
        audit.setUserId(order.getUserId());
        auditDAO.logAudit(audit);  // Assuming this method logs the audit in your DB
    }
}
