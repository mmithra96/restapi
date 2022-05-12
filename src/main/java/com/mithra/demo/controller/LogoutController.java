package com.mithra.demo.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mithra.demo.model.User;
import com.mithra.demo.service.LogoutServiceImpl;

@RestController
@RequestMapping("/api/user")
public class LogoutController {

	@Autowired
	LogoutServiceImpl logoutService;
	private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);

	/*logout() allows us to delete from DataBase. 
	 *1. This method  allows the user to logout.
	 *2. This method returns "User Logged out Successfully.",
	 *3.This method returns "User does not exist",If the user does not exist.
	 *   */
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<?> logout(@Valid @RequestBody User user) {
		logger.info("LogoutController - logout");
		String message = logoutService.logoutUser(user);
		return new ResponseEntity<Object>(message, HttpStatus.OK);
	}

}
