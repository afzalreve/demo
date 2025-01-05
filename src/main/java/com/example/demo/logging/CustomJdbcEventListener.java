package com.example.demo.logging;

import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.common.StatementInformation;
import com.p6spy.engine.event.SimpleJdbcEventListener;

public class CustomJdbcEventListener extends SimpleJdbcEventListener {
    public CustomJdbcEventListener() {
        System.out.println("ClassLoader: " + this.getClass().getClassLoader());
        System.out.println("CustomJdbcEventListener initialized");
    }

    @Override
    public void onBeforeAnyExecute( StatementInformation statementInformation) {
        String sql = statementInformation.getSqlWithValues();
        System.out.println("onBeforeAnyExecute: Before executing SQL: " + sql); // this does not print
        // Implement logic to capture current state, e.g., query the current data
    }

    @Override
    public void onBeforeExecuteUpdate( PreparedStatementInformation statementInformation) {
        String sql = statementInformation.getSqlWithValues();
        System.out.println("onBeforeExecuteUpdate: Before executing SQL: " + sql); // this does not print
        // Implement logic to capture current state, e.g., query the current data
    }
}
