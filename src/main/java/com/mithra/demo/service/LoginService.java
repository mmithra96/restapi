package com.mithra.demo.service;

import org.springframework.stereotype.Service;

import com.mithra.demo.exception.UserNotFoundException;
import com.mithra.demo.exception.UserServiceException;
import com.mithra.demo.model.User;

@Service
public interface LoginService {

	//Allows the user to login throws UserServiceException for DB exceptions and UserNotFoundException if user not found
	String loginUser(User user) throws UserServiceException,UserNotFoundException;

}