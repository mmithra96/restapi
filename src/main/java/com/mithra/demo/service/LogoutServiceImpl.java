package com.mithra.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mithra.demo.exception.UserNotFoundException;
import com.mithra.demo.exception.UserServiceException;
import com.mithra.demo.model.User;
import com.mithra.demo.respository.UserDbRepositoryImpl;

@Service
public class LogoutServiceImpl implements LogoutService {
	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	UserDbRepositoryImpl userRepository;
	
	/*Logs out the existing user, if the user does not exist throws UserNotFoundException*/
	@Override
	public String logoutUser(User user) throws UserServiceException, UserNotFoundException {
		logger.info("Logout Service - User");
		return userRepository.logoutUser(user);
	}

}
