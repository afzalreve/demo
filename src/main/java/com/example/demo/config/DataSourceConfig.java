package com.example.demo.config;

import com.p6spy.engine.spy.P6DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {



//    public DataSourceConfig(@Lazy DataSource realDataSource) {
//        this.realDataSource = realDataSource; // Inject the auto-configured DataSource
//    }
@Bean
@Qualifier("primaryDataSource")
public DataSource realDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/loggerdemo");
    dataSource.setUsername("root");
    dataSource.setPassword("@3unfiled");
    return dataSource;
}

    @Bean
    @Lazy
    @Qualifier("secondaryDataSource")
    public DataSource secondaryDataSource(@Qualifier("primaryDataSource") DataSource realDataSource) {
        // Wrap the real DataSource with P6DataSource
        return new P6DataSource(realDataSource);
    }
}
