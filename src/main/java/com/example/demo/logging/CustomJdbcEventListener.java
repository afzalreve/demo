package com.example.demo.logging;

import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.common.StatementInformation;
import com.p6spy.engine.event.JdbcEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class CustomJdbcEventListener extends JdbcEventListener {
    private static final Logger logger = LoggerFactory.getLogger(CustomJdbcEventListener.class);

    @Override
    public void onAfterExecuteUpdate(PreparedStatementInformation statementInformation, long timeElapsedNanos, int rowCount, SQLException e) {
        logUpdate(statementInformation.getSql());
    }

    @Override
    public void onAfterExecuteUpdate(StatementInformation statementInformation, long timeElapsedNanos, String sql, int rowCount, SQLException e) {
        logUpdate(sql);
    }

    private void logUpdate(String sql) {
        System.out.println("SQL Update Executed: " + sql);
        logger.info("SQL Update Executed: {}", sql);
    }
}
