package com.mithra.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mithra.demo.exception.UserServiceException;
import com.mithra.demo.model.User;

@Service
public interface UserService {
   /*This interface is used for basic CRUD operations */
	
	User createUser(User user) throws UserServiceException;
	List<User> getUsers() throws UserServiceException;
	void deleteUser(String userName);
	String updateUser(User user);
	
}
