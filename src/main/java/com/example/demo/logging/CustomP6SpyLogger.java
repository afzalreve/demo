package com.example.demo.logging;

import com.example.demo.service.MyCustomService;
import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class CustomP6SpyLogger extends com.p6spy.engine.spy.appender.StdoutLogger {

    @Override
    public void logSQL( int connectionId, String now, long elapsed, Category category, String prepared, String sql, String url) {
        System.out.println("Intercepted SQL: " + sql); // this line is not being printed
        if (sql != null && sql.trim().toLowerCase().startsWith("update")) {
            // Log the SQL (optional)
            System.out.println("Intercepted Update SQL: " + sql);

            // Call your custom method
            callCustomMethod(sql);
        }
        super.logSQL(connectionId, now, elapsed, category, prepared, sql, url);
    }

    private void callCustomMethod(String sql) {
        // Your custom method implementation here
        System.out.println("Custom Method Called with SQL: " + sql);
        // Example: perform a specific operation
        MyCustomService.logDatabaseUpdate(sql);
    }
}
