package com.mithra.demo.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes=UserServiceException.class)
public class UserExceptionTest {

	@Test 
	void UserExceptionMethodTest()
	{
		UserServiceException exception = new UserServiceException();
		assertNotNull(exception); // Checking if the exception is not null
		UserServiceException exceptionMessage = new UserServiceException("Erro");
		assertNotNull(exceptionMessage);  // Checking if the exception is not null
		
	}
}
