package com.mithra.demo.service;

import com.mithra.demo.exception.UserNotFoundException;
import com.mithra.demo.exception.UserServiceException;
import com.mithra.demo.model.User;

public interface LogoutService {

	/*logout User*/
	String logoutUser(User user) throws UserServiceException,UserNotFoundException;

}
