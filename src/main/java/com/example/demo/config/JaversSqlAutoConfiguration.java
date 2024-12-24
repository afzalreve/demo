package com.example.demo.config;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.repository.sql.DialectName;
import org.javers.repository.sql.JaversSqlRepository;
import org.javers.repository.sql.SqlRepositoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JaversSqlAutoConfiguration {
    @Bean
    public JaversSqlRepository javersSqlRepository(DataSource dataSource) {
        return SqlRepositoryBuilder.sqlRepository()
                .withConnectionProvider(dataSource::getConnection)
                .withDialect(DialectName.MYSQL)
                .build();
    }

    @Bean
    public Javers javers(JaversSqlRepository javersSqlRepository) {
        return JaversBuilder.javers()
                .registerJaversRepository(javersSqlRepository)
                .build();
    }
}
