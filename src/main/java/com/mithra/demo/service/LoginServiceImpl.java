package com.mithra.demo.service;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mithra.demo.exception.UserNotFoundException;
import com.mithra.demo.exception.UserServiceException;
import com.mithra.demo.model.User;
import com.mithra.demo.respository.UserDbRepository;

@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	UserDbRepository userRepository;
	/* loginUser() - Throws UserServiceException when there is error in Db
	 * Throws UserNotFoundException when the user does not exist
	 *   */
	public String loginUser(User user) throws UserServiceException,UserNotFoundException {

		logger.info("User Login Service - Login User");

		String loginStatus = null;
		try {
			loginStatus = authenticateUser(user);
			if (loginStatus != null) {
				return loginStatus;
			} else {
				logger.info("User Login Service - Login User - Failure - User Not Found");
				throw new UserNotFoundException("User Login Failure : User Not Found");
			}
		} catch (UserServiceException u) {
			logger.info("User Login Service - Login User - Exception : [{}]", u.getMessage());
			throw u;
		}
	}

	/*authenticateUser encrypts and compares the entered password with the password in DB*/
	private String authenticateUser(User user) throws UserServiceException,UserNotFoundException {

		logger.info("User Login Service - Authenticate User");

		String loginStatus = null;

		if (user.getEmailId() != null && user.getPassword() != null) {
			try {
				Optional<User> userOptional = userRepository.findByKey(user);
				if (userOptional.isPresent()) {
					User userExist = userOptional.get();
					String encodedPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
					if (!userExist.getPassword().equals(encodedPassword)) {
						loginStatus = "User Login Error - Invalid Credentials";
						throw new UserNotFoundException(loginStatus);
					} else {
						SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						userExist.setLastLoginDate(formatter.format(new Date()).toString());
						userExist.setIsUserLoggedIn(true);
						userRepository.updateUserItem(userExist);
						loginStatus = "User Login Successful";
					}

				} else {
					logger.info("User Login Service - Authenticate User - Failure - User Not Found");
					loginStatus = "User Login Failure - User Not Found";
				}
			} catch (Exception e) {
				logger.info("User Login Service - Authenticate User - Exception : [{}]", e.getMessage());
				throw e;
			}

		}

		return loginStatus;

	}

}
