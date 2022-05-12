package com.mithra.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mithra.demo.exception.UserNotFoundException;
import com.mithra.demo.model.User;
import com.mithra.demo.service.LoginService;

@RestController
@Validated
@RequestMapping("/api/v1/user/auth")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	// Autowiring LoginService
	@Autowired
	private LoginService userLoginService;

	/* authenticateUser() allows us to login the api, update user in the DataBase. 
	 *1. This method returns "User Login Successful" on successful login .
	 *2. This method returns "Unauthorized!: User Not Found", for invalid user,
	 *3. This method returns "User does not exist",If the user does not exist.
	 *4. This method returns "User Login Error - Invalid Credentials", for invalid credentials.
	 *5. This method returns "UserLoginController - Authenticate User - Unauthorized! - Exception : [{}]"
	 *for DB exceptions.
	 *   */
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> authenticateUser(@RequestBody User user) {
		logger.info("UserLoginController - Authenticate User");
		Map<String, String> response = new HashMap<String, String>();
		ResponseEntity<?> entity = null;
		try {
			String loginStatus = userLoginService.loginUser(user);
			response.put("message", loginStatus);
			entity = new ResponseEntity<Object>(response, HttpStatus.OK);
		} catch (UserNotFoundException uE) {
			logger.info("Unauthorized!: User Not Found");
			response.put("message", "Unauthorized!:" + uE.getMessage());
			entity = new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			logger.info("UserLoginController - Authenticate User - Unauthorized! - Exception : [{}]", e.getMessage());
			response.put("message", "Unauthorized!: " + e.getMessage());
			entity = new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
		}
		return entity;
	}

}
