package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing
@SpringBootApplication
//@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class})
public class DemoApplication {

	public static void main(String[] args) {
//		System.setProperty("p6spy.config.file", "classpath:spy.properties"); // not sure whether this is required
		SpringApplication.run(DemoApplication.class, args);
	}

}
