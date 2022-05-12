package com.mithra.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.mithra.demo.config.AppConfig;
import com.mithra.demo.exception.UserServiceException;
import com.mithra.demo.model.User;
import com.mithra.demo.respository.UserDbRepositoryImpl;
import com.mithra.demo.service.UserServiceImpl;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = { UserDbRepositoryImpl.class, UserServiceImpl.class, AppConfig.class })
public class UserServiceTest {

	@Mock
	UserDbRepositoryImpl mockrepo;
	@InjectMocks
	UserServiceImpl service;

	/* Simple Unit test to check getUsers */
	@Test
	public void getUserTest() throws Exception {
		List<User> tempUsers = new ArrayList<User>();
		User firstUser = new User();
		firstUser.setUserName("testName1");
		tempUsers.add(firstUser);
		User secondUser = new User();
		secondUser.setUserName("testName2");
		tempUsers.add(secondUser);
		when(mockrepo.getUsers()).thenReturn(tempUsers);
		List<User> result = new ArrayList<User>();
		result = service.getUsers();
		assertEquals(result.size(), 2);
	}

	/* Simple Unit test to check getUsers with exception */
	@Test
	public void getUserExceptionTest() throws Exception {

		when(mockrepo.getUsers()).thenThrow(new UserServiceException());
		assertThrows(UserServiceException.class, () -> service.getUsers());

	}
}
