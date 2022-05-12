package com.mithra.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mithra.demo.exception.UserNotFoundException;
import com.mithra.demo.exception.UserServiceException;
import com.mithra.demo.model.*;
import com.mithra.demo.respository.UserDbRepositoryImpl;

@Service
public class UserServiceImpl {

	@Autowired
	UserDbRepositoryImpl userRepo;
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	/*createUser calls the saveUser in the DAO layer using autowired userRepo to save user in DB. */
	public User createUser(User user) throws UserServiceException {
		logger.info("User Service - Create User");
		return userRepo.saveUser(user);
	}

	/*getUsers calls the getUsers in the DAO layer using autowired userRepo to list users */
	public List<User> getUsers() throws UserServiceException {
		logger.info("User Service - Get UsersList");

		List<User> Users = new ArrayList<User>();
		try {
			Users = userRepo.getUsers();
		} catch (Exception e) {
			logger.error("User Service - Get Users List - Exception : [{}]", e.getMessage());
			throw new UserServiceException("User Service - Get Users List - Exception : "+ e.getMessage());
		}
		return Users;

	}
	
	/*deleteUser calls the deleteUser in the DAO layer using autowired userRepo to delete user */
	public void deleteUser(String userName) throws UserNotFoundException {
		logger.info("User Service - Delete User");
		userRepo.deleteUser(userName);
		logger.info("Exiting User Service - Delete User");

	}

	/*updateUser calls the updateUser in the DAO layer using autowired userRepo to update user */
	public String updateUser(User user) {
		logger.info("User Service - Update User");
		return userRepo.updateUser(user);
	}
}
