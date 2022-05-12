package com.mithra.demo.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mithra.demo.config.AppConfig;
import com.mithra.demo.model.User;
import com.mithra.demo.respository.UserDbRepositoryImpl;
import com.mithra.demo.service.LoginServiceImpl;

@AutoConfigureMockMvc // Mock different layers
@EnableWebMvc // Mock environment for the weblayer
@SpringBootTest(classes = { LoginServiceImpl.class, LoginController.class, UserDbRepositoryImpl.class, AppConfig.class,
		HealthController.class })
public class LoginControllerTest {

	// @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) - we
	// automatically start the server using TestRestTemplate

	@Mock
	LoginServiceImpl service;
	@Autowired
	private MockMvc mockMvc;

	@Test /* Method for testing health check endpoint */
	public void healthCheckTest() throws Exception {
		this.mockMvc.perform(get("/api/health")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Health is good")));
	}

	@Test /* Method for testing login endpoint */
	public void authenticateUserTest() throws Exception {
		User user = new User();
		user.setEmailId("test@test.com");
		user.setPassword("testpassword");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = writer.writeValueAsString(user);

		when(service.loginUser(user)).thenReturn("User Login Failure - User Not Found");
		this.mockMvc
				.perform(post("/api/v1/user/auth/login").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("User Login Failure - User Not Found")));
	}

}
