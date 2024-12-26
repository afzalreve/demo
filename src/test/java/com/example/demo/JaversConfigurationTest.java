package com.example.demo;

import com.example.demo.dto.OrderDTO;
import org.javers.core.Javers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JaversConfigurationTest {

    @Autowired
    private Javers javers;

    @Test
    public void verifyJaversConfiguration() {
        System.out.println(javers.getTypeMapping(OrderDTO.class));
    }
}
