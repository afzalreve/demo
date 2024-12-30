package com.example.demo;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class LoggingFilterTest {

    @Autowired
    private WebApplicationContext context;

    @Test
    public void testActiveSessionWithPostRequest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Create a mock session
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("username", "test_user");
        session.setAttribute("role", "admin");

        // Define the JSON body
        String requestBody = """
                {
                    "id": 3,
                    "name": "abcde"
                }
                """;

        // Send a POST request with the mock session and JSON body
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .session(session))
                .andExpect(status().isOk());
    }
}
