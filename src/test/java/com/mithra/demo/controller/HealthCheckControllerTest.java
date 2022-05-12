package com.mithra.demo.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@AutoConfigureMockMvc // Mock different layers
@EnableWebMvc // Mock environment for the weblayer
@SpringBootTest(classes = { HealthController.class })
public class HealthCheckControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test /* Method for testing health check endpoint */
	public void healthCheckTest() throws Exception {
		this.mockMvc.perform(get("/api/health")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Health is good")));
	}

}
