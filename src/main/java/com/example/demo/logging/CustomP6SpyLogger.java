package com.example.demo.logging;

import com.example.demo.service.MyCustomService;
import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.StdoutLogger;

public class CustomP6SpyLogger extends StdoutLogger {
    public CustomP6SpyLogger() {
        System.out.println("CustomP6SpyLogger initialized");
    }

    @Override
    public void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql, String url) {
        // Custom logging logic
        System.out.println("Intercepted SQL: " + sql); // This should now be printed

        if (sql != null && sql.trim().toLowerCase().startsWith("update")) {
            // Log the SQL for 'update' queries
            System.out.println("Intercepted Update SQL: " + sql);

            // Call your custom method (for example, call a service or log the update)
            callCustomMethod(sql);
        }

        // Call the superclass method to keep the default logging functionality
        super.logSQL(connectionId, now, elapsed, category, prepared, sql, url);
    }

    private void callCustomMethod(String sql) {
        // Custom logic you want to apply with the SQL
        System.out.println("Custom Method Called with SQL: " + sql);
        // Example: Call a service that logs database updates
        MyCustomService.logDatabaseUpdate(sql);
    }
}
