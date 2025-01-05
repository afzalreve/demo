package com.example.demo.config;

import com.p6spy.engine.spy.P6DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    private final DataSource realDataSource;

    public DataSourceConfig(DataSource realDataSource) {
        this.realDataSource = realDataSource; // Inject the auto-configured DataSource
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        // Wrap the real DataSource with P6DataSource
        return new P6DataSource(realDataSource);
    }
}
